package com.demo.DataStructures.Stack;


import java.util.HashMap;
import java.util.Map;

/**
 * 基于连续物理内存（数组）的单调栈底层实现
 * 核心优势：零对象创建（无 GC 压力），L1 Cache 极其友好
 */
public class MonotonousStack {

    // 这里只是声明，没有分配连续的内存空间！
    int[] stack;

    // 物理游标：-1 代表当前没有任何有效数据
    int top = -1;

// ==========================================
    // 第一部分：基础物理微操
    // ==========================================

    /**
     * 入栈操作：游标上移，并在新位置写入数据
     */
    public void push(int index) {
        stack[++top] = index;
    }

    /**
     * 出栈操作：逻辑剔除，游标下移，废数据保留在原内存地址（等待被覆盖）
     */
    public int pop() {
        return stack[top--];
    }

    /**
     * 窥探栈顶：只读不删，直接通过游标获取数据
     */
    public int peek() {
        return stack[top];
    }

    // ==========================================
    // 第二部分：高级业务逻辑
    // ==========================================

    /**
     * 核心算法：查找每个元素的左侧第一个严格大于它的元素
     *
     * @param nums 原始输入数组（代表楼房高度）
     * @return res 存放雷达探测报告，值为高墙的绝对物理索引（门牌号），-1 代表无遮挡
     */
    public int[] findLeftGreater(int[] nums) {

        // 【关键节点】：为了防止空指针异常，我们必须在这里为 stack 分配内存！
        stack = new int[nums.length];
        int[] res = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {

            // 1. 大清洗：视线遮挡物理模型。遇到 <= 自己的废数据，无情淘汰
            while (top != -1 && nums[stack[top]] <= nums[i]) {
                top--; // 物理出栈（这里为了极致性能，通常直接操作指针，不调用 pop 方法）
            }

            // 2. 确立历史答案：在最干净的时刻，记录左侧第一堵高墙的门牌号
            if (top == -1) {
                res[i] = -1; // 视野开阔，无高墙
            } else {
                res[i] = stack[top]; // 栈顶存活的就是那堵墙
            }

            // 3. 新王登基：当前门牌号作为未来元素的候选人，压入物理内存
            stack[++top] = i;
        }

        // 详细物理推演与距离计算作用，请参阅 MonotonousStack.html 与 MonotonousStack_res.html
        return res;
    }

    /**
     * 力扣 739. 每日温度 (Daily Temperatures)
     * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，
     * 其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
     * * 【架构师思维导读】
     * 核心模型：单调栈（Monotonic Stack） - 寻找右侧第一个更大元素的物理距离
     * 底层优化：摒弃 java.util.Stack，使用物理连续内存 int[] 实现零 GC 极速出入栈
     */
    public static int[] dailyTemperatures(int[] temperatures) {
        // 如果不懂底层的物理推演，可以运行 daily_temperatures_debugger.html 沙盘进行可视化调试

        int n = temperatures.length;
        // 备忘录：用于记录“未来”高温日子的【绝对物理索引（门牌号）】
        int[] stack = new int[n];
        // 物理栈的游标，-1 代表当前备忘录为空
        int top = -1;

        // 雷达报告：用于存放每一天需要等待的天数
        int[] res = new int[n];

        // 1. 时光机倒流：为了让“今天”能看到“未来”，必须从最后一天往前倒着走
        for (int i = n - 1; i >= 0; i--) {

            // 2. 大清洗（物理遮挡定理）：
            // 如果备忘录里记录的未来某天，温度还没今天高（<=），
            // 那么它对于更早的过去来说，已经被今天这座“高墙”彻底遮挡了，沦为废数据，直接踢出！
            while (top != -1 && temperatures[stack[top]] <= temperatures[i]) {
                top--; // 极速物理出栈
            }

            // 3. 确立历史答案：计算跨度（距离）
            if (top == -1) {
                // 备忘录空了，说明未来没有比今天更热的日子了
                res[i] = 0;
            } else {
                // 找到了未来的高墙！
                // 核心计算：物理等待天数 = 未来的门牌号(stack[top]) - 今天的门牌号(i)
                res[i] = stack[top] - i;
            }

            // 4. 新王登基：把今天的门牌号压入备忘录，作为“昨天”的未来候选人
            stack[++top] = i;
        }

        return res;
    }


    /**
     * 力扣42，接雨水
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     */
    public int trap(int[] height) {
        return 0;
    }


    /**
     * 力扣496. 下一个更大元素 I
     * nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
     * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
     * 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定 nums2[j] 的 下一个更大元素 。如果不存在下一个更大元素，那么本次查询的答案是 -1 。
     * 返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
     */
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // 先去遍历nums2里面的数据，因为我们的nums1是nums2的子集
        int[] stack = new int[nums2.length];
        int top = -1;
        Map<Integer, Integer> res = new HashMap<>();
        for(int i = 0; i <= nums2.length-1; i++){
            // 出栈
            while (top != -1 && stack[top] < nums2[i]){
                top--;
                res.put(stack[top],nums2[i]);
            }
            if(top == -1){
                res.put(nums2[i],-1);
            }else {
                stack[i] = nums2[top];
            }
            stack[++top] = nums2[i];
        }
        return null;
    }
}
