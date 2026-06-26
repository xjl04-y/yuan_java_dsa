package com.demo.Algorithm.Pointers;

/**
 * 快慢指针
 */
public class FastAndSlowPointers {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    /**
     * 环形链表2
     * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 说明：不允许修改给定的链表。
     */
    public static ListNode detectCycle(ListNode head) {
        // 步骤 1：使用快慢指针寻找相遇点
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next; // 快指针每次走 2 步
            slow = slow.next;      // 慢指针每次走 1 步

            if (fast == slow) {    // 如果相遇，说明存在环
                break;
            }
        }

        // 步骤 2：判断退出循环的原因（是找到环了，还是走到了链表尽头）
        if (fast == null || fast.next == null) {
            return null; // 快指针碰到 null，说明无环
        }

        // 步骤 3：寻找环的入口
        // 此时 fast 在相遇点，我们将 slow 放回起点
        slow = head;
        while (slow != fast) {
            // 两个指针以相同的速度（每次 1 步）前进
            slow = slow.next;
            fast = fast.next;
        }

        // 当它们再次相遇时，所在的节点就是环的入口
        return slow;
    }


    /**
     * 链表的中间结点（LeetCode 876）
     * 给定一个非空单链表，返回链表的中间结点。如果有多个中间结点，则返回第二个。
     * * 架构思维：针对“长度未知的单向拓扑结构”，利用 2:1 的相对速度差，
     * 在遍历一次（One Pass）的过程中精确锁定 1/2 处的位置。
     * 完美避免了“先遍历求总长度，再二次遍历找中点”的性能损耗。
     */
    public static ListNode middleNode(ListNode head) {
        // 1. 初始化：快慢指针均在绝对起点就位
        ListNode fast = head;
        ListNode slow = head;

        // 2. 核心推进逻辑：
        // 【边界防御极其关键】利用 && 的短路特性：
        // - fast != null 防御“偶数”长度链表，快指针最终会越界跳入虚无（null）
        // - fast.next != null 防御“奇数”长度链表，快指针最终停在最后一个实体节点上
        while (fast != null && fast.next != null) {
            fast = fast.next.next; // 快指针每次跨越 2 步 (v = 2)
            slow = slow.next;      // 慢指针每次跨越 1 步 (v = 1)
        }

        // 3. 结果产出：
        // 当快指针走完全程 S 时，慢指针刚好走完 S/2 的距离。
        // 由于我们要求偶数时返回“第二个中间节点”，初始 slow=head 和 fast=head 的设定刚好满足此数学规律。
        return slow;
    }


    /**
     * 寻找重复数 (LeetCode 287)
     * 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），
     * 可知数组中至少存在一个重复的整数。 设重复的整数为 x ，且出现 x 次。
     * 约束条件: 1 <= n <= 10^5 , 1 <= nums[i] <= n
     * 架构级要求: 使用常量级 O(1) 的额外空间解决此问题，并要求 O(n) 时间复杂度。
     */
    public static int findDuplicate(int[] nums) {
        // 【物理模型初始化】
        // 下标 0 是绝对的“安全起跑线”。因为数值范围是 1~n，
        // 没有任何位置的数值会是 0，即不存在指向下标 0 的指针。
        int slow = nums[0];
        int fast = nums[0];

        // ==========================================
        // 阶段一：探测时空交汇点 (寻找相遇点)
        // ==========================================
        // 采用 do-while 结构强制引擎启动一次。
        // 完美避开初始状态 slow == fast 导致的逻辑假相遇。
        do {
            // slow.next 语义 -> 通过当前值寻找下一个下标，每次走 1 步
            slow = nums[slow];
            // fast.next.next 语义 -> 连续跳跃两次下标，每次走 2 步
            fast = nums[nums[fast]];
        } while (slow != fast); // 相对速度 v_rel=1，必定能在环内某处精确相遇

        // ==========================================
        // 阶段二：利用 a = c 几何推导，锁定环入口
        // ==========================================
        // 将慢指针放回距离 a 的物理起点。
        // 【边界极易错点】：真正的起点是下标 0，而不是 nums[0]
        slow = 0;

        // 两人以绝对速度 v=1 同步推进，直到再次发生空间重叠
        while (slow != fast) {
            slow = nums[slow]; // 慢指针走距离 a
            fast = nums[fast]; // 快指针走距离 c
        }

        // 再次重叠的坐标 (下标)，就是指向环内的那个“多进单出的数值” -> 重复数
        return slow;
    }


    /**
     * 删除排序数组中的重复项 (LeetCode 26)
     * 核心逻辑：读写指针。slow 始终指向“下一个待写入”的位置。
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 因为 k=1，数组第一个元素（下标 0）必然保留
        // 所以读指针 fast 和写指针 slow 都从下标 1 开始
        int fast = 1;
        int slow = 1;

        while (fast < nums.length) {
            // 核心判断：当前读到的数字 nums[fast] 是否和“已确定的序列”中最后一个有效数字不同？
            // 因为 k=1，所以对比对象是 nums[slow - 1]
            if (nums[fast] != nums[slow - 1]) {
                nums[slow] = nums[fast]; // 发现新数字，写入 slow 指向的位置
                slow++; // 移动写指针，为下一个新数字腾位置
            }
            fast++; // 读指针继续向后扫描
        }

        // slow 的数值刚好就是新数组的长度
        return slow;
    }

    /**
     * 删除排序数组中的重复项 II (LeetCode 80)
     * 核心逻辑：利用有序性，对比当前元素与已填入序列中倒数第 k 个元素。
     */
    public static int removeDuplicatesDouble(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 如果数组长度小于等于 2，必然符合“最多重复两次”的要求
        if (nums.length <= 2) {
            return nums.length;
        }

        // 因为 k=2，数组前两个元素（下标 0 和 1）可以无脑保留
        // 所以写指针 slow 从下标 2 开始，读指针 fast 也从下标 2 开始
        int fast = 2;
        int slow = 2;

        while (fast < nums.length) {
            // 核心判断：当前数字 nums[fast] 是否和已填入序列中“倒数第二个”有效数字不同？
            // 如果 nums[fast] == nums[slow - 2]，根据升序特性，nums[slow - 1] 必然也相等，
            // 此时再写入 nums[fast] 就会导致 3 个连续重复，因此必须跳过。
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast]; // 允许写入（当前数字重复次数未超过 2 次）
                slow++; // 移动写指针
            }
            fast++; // 读指针继续向后扫描
        }

        return slow;
    }

    /**
     * 力扣19. 删除链表的倒数第 N 个结点
     * 核心思想：利用前后双指针拉开一个固定长度为 n 的“滑动窗口”
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;

        // 1. 让 fast 指针先走 n 步，这样 fast 和 slow 之间就隔了 n 个节点的距离 📏
        for(int i = 0; i < n; i++) {
            fast = fast.next;
        }

        // 2. 致命边界处理：如果 fast 走完 n 步后直接指向了 null
        // 说明链表总长度正好是 n，我们要删除的正好是头节点 (head)
        // 直接返回头节点的下一个节点即可，原头节点会被垃圾回收机制清理掉
        if(fast == null) {
            return head.next;
        }

        // 3. 两人保持这个固定的距离，同时往前走
        // 注意条件是 fast.next != null，这样当 fast 走到最后一个节点时就会停下来
        // 此时跑在后面的 slow 就会精确地停在【要删除节点的前驱节点】上 🎯
        while (fast != null && fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 4. 执行删除操作：跳过 slow 紧挨着的下一个节点 ✂️
        if (slow != null) {
            slow.next = slow.next.next;
        }

        // 5. 返回修改后的原链表头节点
        return head;
    }
}
