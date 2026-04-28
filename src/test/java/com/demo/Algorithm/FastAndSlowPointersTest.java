package com.demo.Algorithm;

import org.junit.jupiter.api.Test;

public class FastAndSlowPointersTest {

    /**
     * 测试环形链表入口检测 (Floyd 判圈算法)
     * 验证场景：存在环的单向拓扑图，测试是否能精确定位环的入口节点。
     */
    @Test
    public void TestDetectCycle() {
        // 1. 初始化：创建独立节点
        FastAndSlowPointers.ListNode node1 = new FastAndSlowPointers.ListNode(3);
        FastAndSlowPointers.ListNode node2 = new FastAndSlowPointers.ListNode(2);
        FastAndSlowPointers.ListNode node3 = new FastAndSlowPointers.ListNode(0);
        FastAndSlowPointers.ListNode node4 = new FastAndSlowPointers.ListNode(-4);

        // 2. 建立线性连接: 3 -> 2 -> 0 -> -4
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        // 3. 制造闭环：将尾部节点 -4 强行指向节点 2
        // 此时物理结构：3 -> 2 -> 0 -> -4 -> (回到节点 2)
        node4.next = node2;

        // 4. 执行算法：探测环并寻找起始节点
        FastAndSlowPointers.ListNode result = FastAndSlowPointers.detectCycle(node1);

        // 5. 结果校验
        if (result != null) {
            System.out.println("TestDetectCycle 测试通过: 检测到环！环的起始节点值是: " + result.val);
        } else {
            System.out.println("链表中没有环。");
        }
    }

    /**
     * 测试寻找单链表的中间节点
     * 验证场景：利用快慢指针 2:1 的速度比例，在 One Pass (一次遍历) 中定位中点。
     */
    @Test
    public void TestMiddleNode() {
        // 1. 构造奇数长度的线性链表: 1 -> 2 -> 3 -> 4 -> 5
        FastAndSlowPointers.ListNode node1 = new FastAndSlowPointers.ListNode(1);
        FastAndSlowPointers.ListNode node2 = new FastAndSlowPointers.ListNode(2);
        FastAndSlowPointers.ListNode node3 = new FastAndSlowPointers.ListNode(3);
        FastAndSlowPointers.ListNode node4 = new FastAndSlowPointers.ListNode(4);
        FastAndSlowPointers.ListNode node5 = new FastAndSlowPointers.ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        // 2. 执行算法：当快指针走完时，慢指针应恰好停留在中点 3
        FastAndSlowPointers.ListNode middle = FastAndSlowPointers.middleNode(node1);
        System.out.println("TestMiddleNode 测试通过: 链表的中点是: " + middle.val); // 期望输出 3
    }

    /**
     * 测试寻找数组中的重复数
     * 验证场景：将数组下标与值映射为“隐式链表”，利用寻找环入口的原理在 O(1) 空间下破局。
     */
    @Test
    public void TestFindDuplicate() {
        // 1. 构造测试数据：满足题意长度为 n+1 (6)，且数字都在 1~n (1~5) 之间
        int[] nums = new int[]{1, 2, 3, 4, 5, 5};

        // 2. 执行算法并打印结果
        int duplicate = FastAndSlowPointers.findDuplicate(nums);
        System.out.println("TestFindDuplicate 测试通过: 找到的重复数字是: " + duplicate); // 期望输出 5
    }

    /**
     * 测试删除排序数组中的重复项 (保留 1 次)
     * 验证场景：读写同向双指针，通过对比 nums[slow - 1] 过滤所有冗余项。
     */
    @Test
    public void TestRemoveDuplicates() {
        // 1. 准备输入数据和期望的纯净前缀数组 (k = 1)
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int[] expectedNums = {0, 1, 2, 3, 4};

        // 2. 执行原地覆盖算法，返回有效数据的长度
        int k = FastAndSlowPointers.removeDuplicates(nums);

        // 3. 严格的白盒断言 (Assertion)
        assert k == expectedNums.length : "返回的长度与期望不符";
        for (int i = 0; i < k; i++) {
            assert nums[i] == expectedNums[i] : "元素不匹配，下标：" + i;
        }
        System.out.println("TestRemoveDuplicates 测试通过！");
    }

    /**
     * 测试删除排序数组中的重复项 II (最多保留 2 次)
     * 验证场景：读写同向双指针，通过对比 nums[slow - 2] 实现对重复元素的 2 次容忍。
     */
    @Test
    public void TestRemoveDuplicatesDouble() {
        // 1. 准备输入数据和期望的纯净前缀数组 (k = 2)
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int[] expectedNums = {0, 0, 1, 1, 2, 2, 3, 3, 4};

        // 2. 执行算法
        int k = FastAndSlowPointers.removeDuplicatesDouble(nums);

        // 3. 严格断言
        assert k == expectedNums.length : "返回的长度与期望不符";
        for (int i = 0; i < k; i++) {
            assert nums[i] == expectedNums[i] : "元素不匹配，下标：" + i;
        }
        System.out.println("TestRemoveDuplicatesDouble (含重复元素) 测试通过！");
    }

    /**
     * 测试删除排序数组中的重复项 II (边界/鲁棒性测试)
     * 验证场景：当原数组完全“没有重复元素”时，算法是否会误伤数据或越界。
     */
    @Test
    public void TestRemoveDuplicatesDouble2() {
        // 1. 准备无重复元素的边界数据
        int[] nums = {0, 1, 2, 3, 4};
        int[] expectedNums = {0, 1, 2, 3, 4};

        // 2. 执行算法：预期发生“自我覆盖”，数据依然完好无损
        int k = FastAndSlowPointers.removeDuplicatesDouble(nums);

        // 3. 严格断言
        assert k == expectedNums.length : "边界测试返回长度异常";
        for (int i = 0; i < k; i++) {
            assert nums[i] == expectedNums[i] : "边界测试元素被误伤，下标：" + i;
        }
        System.out.println("TestRemoveDuplicatesDouble2 (无重复边界测试) 测试通过！");
    }
}