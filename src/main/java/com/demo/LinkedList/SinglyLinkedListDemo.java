package com.demo.LinkedList;

/**
 * 单向链表实现
 * 这是一个链表数据结构，每个节点都包含数据和指向下一个节点的引用。
 */
public class SinglyLinkedListDemo {
    Node head; // 链表的头节点，指向链表的第一个元素
    int size; // 链表中元素的数量

    /**
     * 内部类，定义了链表中的节点结构。
     * 每个节点包含一个整数值和一个指向下一个节点的引用。
     */
    private static class Node {
        int data;  // 节点存储的整数数据
        Node next;  // 指向链表中下一个节点的引用

        /**
         * Node 类的构造函数。
         *
         * @param data 要存储在节点中的数据。
         * @param next 对链表中下一个节点的引用。
         */
        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * 合并两个有序链表
     * 核心思路：利用哨兵节点作为新链表的起点，通过比较两个原链表的头节点，
     * 像穿针引线一样把节点按顺序连接起来。
     * 如果看不到代码，可以去看MergeTwoListsDemo.html这个动画演示
     */
    public SinglyLinkedListDemo mergeTwoLists(SinglyLinkedListDemo list1, SinglyLinkedListDemo list2) {
        // 1. 🛠️ 初始化：创建一个哨兵节点 (DummyNode)，它的 data 是多少不重要，
        // 因为它的作用只是作为新链表的“挂钩”。
        Node DummyNode = new Node(0, null);

        // 2. 📍 准备一个游标 (curr)，它像缝纫机的针头，始终指向新链表的末尾。
        Node curr = DummyNode;

        // 3. 👥 准备两个指针，分别指向两个链表的当前排头兵。
        Node p1 = list1.head;
        Node p2 = list2.head;

        // 4. 🚑 边界处理：如果其中一个链表为空，直接返回另一个即可。
        if (p1 == null) return list2;
        if (p2 == null) return list1;

        // 5. ⚖️ 比较与缝合：只要两个队伍都有人，就进行 PK。
        while (p1 != null && p2 != null) {
            if (p1.data <= p2.data) {
                // 如果 p1 较小或相等，把 p1 缝在 curr 后面
                curr.next = p1;
                // p1 队伍向前进一位
                p1 = p1.next;
            } else {
                // 否则把 p2 缝在 curr 后面
                curr.next = p2;
                // p2 队伍向前进一位
                p2 = p2.next;
            }
            // 📍 关键：缝好一个节点后，游标 curr 必须移动到刚才缝好的节点上
            curr = curr.next;
        }

        // 6. 🏁 残局处理：循环结束说明有一个队伍排完了。
        // 因为原链表是有序的，直接把剩下的一整段接到新链表末尾即可。
        if (p1 == null) {
            curr.next = p2;
        } else {
            curr.next = p1;
        }

        // 7. 🎁 包装返回：创建一个新的链表容器。
        SinglyLinkedListDemo list = new SinglyLinkedListDemo();
        // ⚠️ 重点：新链表的头节点是哨兵节点的【下一个】，从而避开辅助用的 0。
        list.head = DummyNode.next;

        return list;
    }


    /**
     * 在链表的开头添加一个新元素。
     *
     * @param data 要添加的元素的值。
     */
    public void addFirst(int data) {
        // 为新数据创建一个新节点。
        Node newNode = new Node(data, null);
        // 将新节点的 next 指针指向当前的头节点。
        newNode.next = this.head;
        // 将链表的头更新为这个新创建的节点。
        this.head = newNode;
        // 链表的大小加一。
        size++;
    }

    /**
     * 获取并返回指定索引处的元素值。
     *
     * @param index 要获取的元素的索引（从0开始）。
     * @return 返回索引处的元素值。如果索引无效（小于0或大于等于size），则返回-1。
     */
    public int get(int index) {
        // 检查索引是否在有效范围内。
        if (index < 0 || index >= size) {
            return -1; // 索引越界，返回-1。
        }
        // 从头节点开始遍历。
        Node p = this.head;
        // 循环移动到指定的索引位置。
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        // 返回目标节点的数据。
        return p.data;
    }

    /**
     * 在链表的末尾添加一个新元素。
     *
     * @param data 要添加的元素的值。
     */
    public void addLast(int data) {
        // 找到链表的最后一个节点。
        Node p = findLast();
        // 如果链表为空（即找不到最后一个节点），则调用 addFirst 方法。
        if (p == null) {
            addFirst(data);
            return;
        }
        // 将原最后一个节点的 next 指针指向新创建的节点。
        p.next = new Node(data, null);
        // 链表的大小加一。
        size++;
    }

    /**
     * 一个私有辅助方法，用于查找并返回链表的最后一个节点。
     *
     * @return 返回链表的最后一个节点。如果链表为空，则返回 null。
     */
    private Node findLast() {
        // 如果链表为空，直接返回 null。
        if (this.head == null) {
            return null;
        }
        // 从头节点开始。
        Node p = this.head;
        // 循环直到找到一个没有下一个节点的节点，即最后一个节点。
        while (p.next != null) {
            p = p.next;
        }
        // 返回最后一个节点。
        return p;
    }

    /**
     * 获取并返回链表中元素的总数。
     *
     * @return 链表的大小。
     */
    public int getSize() {
        return size;
    }

    /**
     * 遍历链表并将其所有元素打印到控制台。
     * 元素之间用空格分隔。
     */
    public void display() {
        // 从头节点开始遍历。
        Node p = this.head;
        while (p != null) {
            // 打印当前节点的数据。
            System.out.print(p.data + " ");
            // 移动到下一个节点。
            p = p.next;
        }
    }

    /**
     * 从链表中删除第一个具有指定值的节点。
     *
     * @param data 要删除的节点的值。
     */
    public void remove(int data) {
        // 从头节点开始查找。
        Node current = this.head;
        // prev 用于追踪 current 节点的前一个节点。
        Node prev = null;

        // 如果链表为空，则直接返回。
        if (current == null) return;

        // 情况1：要删除的是头节点。
        if (current.data == data) {
            this.head = current.next; // 将头指向下一个节点。
            size--; // 大小减一。
            return; // 完成并退出。
        }

        // 情况2：要删除的节点在链表中间或末尾。
        // 循环查找值为 data 的节点。
        while (current != null && current.data != data) {
            prev = current; // prev 跟随 current。
            current = current.next; // current 向前移动。
            // 在循环内部检查是否已到达链表末尾但仍未找到。
            if (current == null) {
                System.out.println("数据不存在");
                return; // 数据不存在，退出方法。
            }
        }

        // 如果循环结束（因为找到了数据），则 current 指向要删除的节点。
        // 将前一个节点的 next 指针“跳过”current，指向 current 的下一个节点。
        prev.next = current.next;
        // 链表大小减一。
        size--;
    }

    /**
     * 在指定索引位置插入新节点
     *
     * @param data  要插入的数据
     * @param index 目标索引位置
     */
    public void insert(int data, int index) {
        // 1. 边界检查：索引不能小于0，也不能超过当前链表的长度
        // 注意：index == size 是允许的，这相当于在末尾追加
        if (index < 0 || index > size) return;

        // 2. 特殊情况处理：如果是在队尾插入
        if (index == size) {
            addLast(data); // addLast 内部会处理 size++ 和空表情况
            return;
        }

        // 3. 特殊情况处理：如果是在队首插入（或者链表本身为空）
        if (index == 0) {
            addFirst(data); // addFirst 内部会处理 size++
            return;
        }

        // 4. 中间位置插入逻辑
        Node current = this.head;
        Node prev = null;

        // 移动指针，找到索引为 index 的节点（current）及其前驱节点（prev）
        for (int i = 0; i < index; i++) {
            prev = current;
            current = current.next;
        }

        // 核心动作：创建新节点，让它的 next 指向 current（接管后续部队）
        // 然后让 prev.next 指向新节点（完成前驱重连）
        prev.next = new Node(data, current);

        // 5. 更新账本：只有在这里手动增加 size，因为前面的 addFirst/addLast 已经加过了
        size++;
    }

    /**
     * 替换指定索引节点的数据
     *
     * @param index 目标索引
     * @param data  新的数据
     * @return 被替换掉的旧数据
     */
    public int set(int index, int data) {
        // 索引必须在有效范围内 [0, size-1]
        if (index < 0 || index >= size) return -1;

        Node p = this.head;
        // 遍历到目标位置
        for (int i = 0; i < index; i++) {
            p = p.next;
        }

        // 保存旧值用于返回，然后更新数据
        int temp = p.data;
        p.data = data;
        return temp;
    }

    /**
     * 检查链表中是否存在指定的数据
     *
     * @param data 要查找的目标值
     * @return 如果找到返回 true，否则返回 false
     */
    public boolean contains(int data) {
        Node p = this.head; // 从头节点开始
        while (p != null) { // 只要没走到链表尽头
            if (p.data == data) return true; // 发现目标，立即返回成功
            p = p.next; // 移动到下一个节点
        }
        return false; // 遍历完整个链表都没找到
    }

    /**
     * 查找指定数据在链表中的索引位置
     *
     * @param data 要查找的目标值
     * @return 返回目标值的索引（从0开始），如果不存在则返回 -1
     */
    public int indexOf(int data) {
        Node p = this.head; // 从头节点开始
        int index = 0; // 记录当前走到了第几个位置
        while (p != null) {
            if (p.data == data) return index; // 发现目标，返回当前计数值
            p = p.next; // 移动指针
            index++; // 索引累加
        }
        return -1; // 没找到，返回 -1
    }

    /**
     * 清空链表
     * 由于java自带gcc垃圾回收，所以这个方法可以有效地清空链表
     */
    public void clear() {
        this.head = null;
        this.size = 0;

    }

    /**
     * 反转链表
     * 核心逻辑：通过“过去、现在、未来”三个指针的平移，让每个节点依次“转身”。
     */
    public void Inversion() {
        // 如果不理解去看LinkListInversionDemo.html这个文件
        // 基础防御：如果链表为空，直接返回
        if (head == null) return;

        // 【现在】：从头节点开始起步
        Node curr = this.head;
        // 【过去】：反转后原头节点会变成尾节点，所以它指向的“过去”初始化为 null
        Node prev = null;

        while (curr != null) {
            // 1. 记下【未来】：在改变指向前，先派 next 去拉住原本的下一个节点，防止断路
            Node next = curr.next;

            // 2. 原地转身：让【现在】节点的 next 指向【过去】
            curr.next = prev;

            // 3. 更新【过去】：【现在】已经处理完了，它成为了下一轮的【过去】
            prev = curr;

            // 4. 更新【现在】：让指针走向刚才记下的【未来】，准备处理下一个节点
            curr = next;
        }

        // 5. 重新指派首领：当循环结束，prev 停在原链表的最后一个节点，它就是新链表的头
        this.head = prev;
    }



}