package com.demo.LinkedList;

/**
 * 双向链表 (Doubly Linked List) 完整实现与避坑指南
 * * 核心特性：
 * 1. 每个节点不仅有右手（next）指向下一个，还有左手（prev）指向上一个。
 * 2. 优势：可以双向遍历，在已知某个节点时，可以直接访问它的前驱，安全性（鲁棒性）更高。
 * 3. 难点：每次插入或删除，通常需要处理 4 根指针的断开与重连，极易发生 NullPointerException（空指针异常）。
 */
public class DoublyLinkedListDemo {
    // 链表的大管家，永远指向队伍的第一个节点。如果为空，说明链表是空的。
    Node head;
    // 链表的账本，记录当前队伍里有多少人
    int size;
    /**
     * 内部类：链表的节点
     */
    private static class Node {
        int data;   // 节点存放的数据
        Node next;  // 右手：指向下一个节点
        Node prev;  // 左手：指向上一个节点

        public Node(int data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * 在链表头部添加新节点
     */
    public void addFirst(int data) {
        // 如果不理解内存变动，可以去回顾 DoublyLinkdListAddfirstDemo.html 这个文件

        // 1. 诞生新节点：此时它的 next 和 prev 都是 null
        Node newNode = new Node(data, null, null);

        // 2. 特殊情况：开天辟地的第一个节点
        if (head == null) {
            newNode.next = head; // 其实就是 null
            head = newNode;      // 大管家直接指向它
            size++;
            return;
        }

        // 3. 常规情况：新老交接
        newNode.next = head;     // 新人的右手牵住老头领
        head.prev = newNode;     // 老头领的左手回头牵住新人 (注意：如果前面不拦截 head==null，这行就会报空指针！)
        head = newNode;          // 移交大管家的指向
        size++;
    }

    /**
     * 在双向链表末尾添加新节点
     */
    public void addLast(int data) {
        // 1. 如果链表为空，直接复用 addFirst 方法 🚀
        // 这样可以确保 head 被正确初始化，避免在空链表上找末尾导致崩溃
        if(this.head == null){
            addFirst(data);
            return;
        }

        // 2. 找到当前链表的最后一个节点
        Node last = findLast();

        // 3. 建立双向连接 🤝：
        // new Node(data, null, last) 内部执行了两个动作：
        //   a. 设置新节点的 next 为 null (因为它是新的队尾)
        //   b. 设置新节点的 prev 指向旧的 last
        // 然后 last.next = ... 让旧队尾的右手牵住新节点
        last.next = new Node(data, null, last);

        // 4. 维护链表账本，长度加 1
        size++;
    }

    /**
     * 辅助方法：遍历链表找到最后一个节点
     * 时间复杂度是 O(n)，因为需要从头一步步走到尾 🐢
     */
    private Node findLast(){
        Node p = this.head;
        // 关键点：判断“下一个是不是空的” (p.next != null)
        // 如果我们写成 p != null，循环结束时 p 就变成 null 了，什么也拿不到！
        while (p.next != null){
            p = p.next;
        }
        return p;
    }

    /**
     * 打印链表数据 (用于调试和展示)
     */
    public void display() {
        Node p = this.head;
        while(p != null){
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    /**
     * 获取链表长度
     */
    public int getSize() {
        return size;
    }

    /**
     * 根据索引查询数据
     * 时间复杂度: O(n)
     */
    public int get(int index){
        // 安全检查：防止索引越界
        if(index < 0 || index >= size){
            return -1; // 或者抛出 IndexOutOfBoundsException
        }
        Node p = this.head;
        for(int i = 0; i < index; i++){
            p = p.next;
        }
        return p.data;
    }

    /**
     * 根据索引修改数据
     * 时间复杂度: O(n)
     */
    public void set(int data, int index){
        if(index < 0 || index >= size){
            return;
        }
        Node p = this.head;
        for(int i = 0; i < index; i++){
            p = p.next;
        }
        p.data = data;
    }

    /**
     * 在指定索引位置插入节点
     * @param data  要插入的数据
     * @param index 索引位置 (0 到 size)
     */
    public void insert(int data, int index) {
        // 1. 越界合法性检查
        if (index < 0 || index > size) {
            return;
        }

        // 2. 边界情况优化：如果是在末尾插入，复用 addLast
        if (index == size) {
            addLast(data);
            return;
        }

        // 3. 边界情况优化：如果是在头部插入，复用 addFirst
        if (index == 0) {
            addFirst(data);
            return;
        }

        // 4. 寻找插入点的前驱节点 (寻找 index-1 处的节点)
        Node curr = this.head;
        for (int i = 0; i < index - 1; i++) {
            curr = curr.next;
        }

        /* * 5. 核心插入逻辑 (利用赋值运算的先后顺序)：
         * 等号右边先执行：此时 curr.next 还是“原来的后辈地址”。
         * 新节点 Node 诞生后，它的右手(next)拉着后辈，左手(prev)拉着 curr(前辈)。
         * 等号左边最后执行：curr.next 才被覆盖为新节点的地址。
         * * 如果对这里的“时钟周期/赋值时差”有疑问，去复习 DoubleLinkdListInsertNewNodeDemo.html。
         */
        curr.next = new Node(data, curr.next, curr);

        /* * 6. 修复后辈的回头指针 (prev)：
         * ⚠️ 曾经犯的错：写成了 curr.next.prev = curr.next; (导致新节点自己指向自己)
         * 正确逻辑：此时 curr.next 已经是【新节点】了。
         * 那么 curr.next.next 才是【原来的那个后辈】。
         * 必须加 != null 判断，否则当插在倒数第二个位置时，依然可能报空指针。
         */
        if (curr.next.next != null) {
            curr.next.next.prev = curr.next;
        }

        // 7. 维护链表计数器
        size++;
    }

    /**
     * 根据数据值删除遇到的第一个节点
     * @param data 要删除的目标数据
     */
    public void remove(int data) {
        Node p = this.head;

        // 1. 空链表检查
        if(p == null) {
            System.out.println("链表为空");
            return;
        }

        // 2. 查找目标节点
        while (p.data != data) {
            p = p.next;
            // ⚠️ 曾经犯的错：忘记在循环内检查 p == null。
            // 修复：如果数据根本不存在，必须及时停下来，防止访问 null.data 导致崩溃。
            if(p == null) {
                System.out.println("链表中没有该数据");
                return;
            }
        }

        // 3. 场景 A：删除的是头节点 [head]
        if(p == head) {
            head = p.next; // 大管家移交给下一个节点

            /* * ⚠️ 潜伏的错误点：
             * 如果链表里原本只有一个节点，删掉后 head 变成了 null。
             * 此时如果不加判断直接执行 head.prev = null，会报空指针异常。
             * 修复：只有当后面还有人(head != null)时，才让新头领的“左手”放空。
             */
            if (head != null) {
                head.prev = null;
            }

            size--;
            return;
        }

        // 4. 场景 B：删除的是尾节点 [tail]
        if(p.next == null) {
            // 既然能走到这里，说明既不是头节点也不是空链表，p.prev 一定存在
            p.prev.next = null; // 让倒数第二个人放开右手
            size--;
            return;
        }

        // 5. 场景 C：删除的是中间节点
        // 核心思想：前后两名队友互相拉手，直接跳过/孤立当前节点 p
        p.next.prev = p.prev; // 后面的人左手牵前面的人
        p.prev.next = p.next; // 前面的人右手牵后面的人
        size--;
    }


    public void Inversion() {
        Node curr = this.head;
        Node last = null;
        while (curr!=null){
            // 负责记录下一个节点
            Node p = curr.next;
            // head<->10<->20<->30<->40<->50
            Node prev = curr.prev;
            Node next = curr.next;
            curr.prev = next;
            curr.next = prev;
            last = curr;
            curr = p;
        }
        this.head = last;
    }



}
