package com.demo.DataStructures.Queue;

/**
 * 高性能单线程环形队列 (基于底层连续内存与位运算架构)
 * <p>
 * 设计哲学:
 * 1. 零 GC: 预分配定长连续数组，运行时绝不产生新对象。
 * 2. 零分支: 使用绝对单调递增游标，摒弃传统的绕圈归零 (if tail == capacity) 逻辑。
 * 3. 极速寻址: 强制数组容量为 2 的幂次方，用按位与 (& mask) 替代昂贵的取模 (%) 指令。
 */
public class RingBuffer<E> {

    // 物理连续的泛型数组，存放真实数据的引用。
    // 使用 final 语义，保证系统运行期间引用绝对不可变，杜绝动态扩容带来的 STW 灾难。
    private final E[] items;

    // 寻址掩码，物理含义为 capacity - 1。
    // 使用 final 缓存，让 CPU 在执行 sequence & mask 时达到 1 个时钟周期的极限速度。
    private final int mask;

    /**
     * 架构级构造函数：物理容量对齐与初始化
     *
     * @param capacity 用户期望的容量（可能会被向上抹平对齐）
     */
    public RingBuffer(int capacity) {
        // 校验：必须大于 0，且利用 (capacity & (capacity - 1)) 校验是否已经是 2 的幂次方
        if (!(capacity > 0 && (capacity & (capacity - 1)) == 0)) {
            // 最高位涂抹算法 (Bit Smearing) - O(1) 绝对确定性时间复杂度
            // 减 1 避坑：防止入参已经是 2 的幂（如 16）时，发生无意义的翻倍扩容
            int n = capacity - 1;

            // 无分支流水线：利用无符号右移与按位或，将最高位的 1 向低位疯狂复制
            n = n | (n >>> 1);  // 盖出 2 个 1
            n = n | (n >>> 2);  // 盖出 4 个 1
            n = n | (n >>> 4);  // 盖出 8 个 1
            n = n | (n >>> 8);  // 盖出 16 个 1
            n = n | (n >>> 16); // 盖出 32 个 1（32位 int 全覆盖）

            // 涂抹完毕后，n 变成了一个全 1 的二进制数（如 00001111）。加 1 进位，逼近最近的 2 的幂。
            capacity = n + 1;
        }

        // 强转 Object 数组以绕过 Java 泛型擦除机制
        this.items = (E[]) new Object[capacity];
        // 固化寻址掩码
        this.mask = capacity - 1;
    }

    // 读游标 (读计数器)：记录历史总读取量。单调递增，永不回头。
    private long head;

    // 写游标 (写计数器)：记录历史总尝试写入量。单调递增，永不回头。
    private long tail;

    /**
     * 入队操作 (写动作)
     * 时序铁律: 先判满 -> 再存入物理内存 -> 最后游标递增放行
     *
     * @param element 待入队元素
     * @return 成功返回 true，队列满返回 false
     */
    public boolean enqueue(E element) {
        // 判满逻辑：历史总写入量 - 历史总读取量 == 物理容量 (mask + 1)
        // 此时代表物理队列积压的数据已经顶到了读游标的后背，再写就会覆盖未读数据。
        if(tail - head == mask + 1 ){
            return false;
        }
        // 极速寻址：当前的写游标 & 掩码，将绝对序列号投射到物理内存槽位
        items[(int)tail & mask] = element;
        // 打卡计件：写动作彻底完成后，游标向前推进
        tail++;
        return true;
    }

    /**
     * 出队操作 (读动作)
     * 时序铁律: 先判空 -> 再读取物理内存 -> 清理内存切断引用 (Help GC) -> 最后游标递增放行
     *
     * @return 成功返回元素，队列空返回 null
     */
    public E dequeue() {
        // 判空逻辑：只要历史总读取量追上了历史总写入量，即代表队列被彻底抽干。
        if(tail == head){
            return null;
        }

        // 暂存目标数据的引用
        E e = items[(int) head & mask];
        // 架构师洁癖 (Help GC)：将物理槽位置为 null，切断强引用，防止对象滞留引发内存泄漏
        items[(int) head & mask] = null;
        // 打卡计件：读动作彻底完成后，游标向前推进
        head++;

        return e;
    }
}