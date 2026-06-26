package com.demo.Algorithm.Pointers;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 前缀和学习
 * 核心思想：
 * 通过预处理 prefixSum 数组，把“区间求和”问题转化为 O(1) 查询
 * prefixSum[i] 表示：
 * nums[0] + nums[1] + ... + nums[i]
 */
public class PrefixSum {

    /**
     * LeetCode 303
     * NumArray：前缀和数据结构封装类
     * 设计目的：
     * - 构建阶段 O(n)
     * - 查询阶段 O(1)
     * 本质：
     * 用一个“状态数组”替代重复区间计算
     */
    static class NumArray {

        /**
         * prefixSum[i]：
         * 表示 nums[0..i] 的累加和
         * 例如：
         * nums = [1,2,3]
         * prefixSum = [1,3,6]
         */
        private int[] prefixSum;

        /**
         * 构造函数：
         * 用 nums 初始化 prefixSum
         * 时间复杂度：O(n)
         */
        public NumArray(int[] nums) {

            // 边界保护（实际工程中必须考虑）
            if (nums == null || nums.length == 0) {
                prefixSum = new int[0];
                return;
            }

            prefixSum = new int[nums.length];

            // 初始化第一个元素
            prefixSum[0] = nums[0];

            // 构建前缀和数组
            // 关键点：当前值 = 前一个前缀 + 当前 nums
            for (int i = 1; i <= nums.length - 1; i++) {
                prefixSum[i] = prefixSum[i - 1] + nums[i];
            }
        }

        /**
         * 区间查询 [left, right]
         * 核心公式：
         * sum(left, right) = prefixSum[right] - prefixSum[left - 1]
         * 特例：
         * left == 0 时，不能越界，直接返回 prefixSum[right]
         */
        public int sumRange(int left, int right) {

            if (left == 0) {
                return prefixSum[right];
            }

            return prefixSum[right] - prefixSum[left - 1];
        }
    }

    /**
     * LeetCode 724
     * 给你一个整数数组 nums ，请计算数组的 中心下标 。
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
     * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。]
     */
    public static int pivotIndex(int[] nums) {
        // 由于我们总和=左边总和+中心坐标+右边总和，所以得知
        // 左边总和=总和-中心坐标-右边总和，所以我们这里不需要去算我们的前缀和数组，直接算滚动前缀
        // total：整个数组的总和
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
        }

        // leftTotal：表示当前 i 左边区间 [0..i-1] 的和
        int leftTotal = 0;

        for (int i = 0; i < nums.length; i++) {

            // right = total - left - nums[i]
            // 因为 nums[i] 是“分割点”，不属于左右任何一边
            int rightTotal = total - leftTotal - nums[i];

            // 判断左右是否相等
            if (leftTotal == rightTotal) {
                return i;
            }

            // 更新 leftTotal，让 i 成为下一轮的左边
            leftTotal += nums[i];
        }

        return -1;
    }


    /**
     * LeetCode 560
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
     * 子数组是数组中元素的连续非空序列。
     */
    public static int subarraySum(int[] nums, int k) {
        // 历史前缀和表：key = 某个历史前缀和值，value = 这个前缀和值出现过几次
        HashMap<Integer, Integer> map = new HashMap<>();
        // 空前缀：数组开始之前，前缀和为 0，出现 1 次
        // 用来统计从下标 0 开始、和为 k 的子数组
        map.put(0, 1);
        // 当前扫描到 r 时，nums[0..r] 的前缀和
        int prefixSum = 0;
        // 已找到的和为 k 的连续子数组数量
        int ans = 0;

        for (int r = 0; r < nums.length; r++) {
            // 更新当前右端点 r 对应的前缀和
            prefixSum += nums[r];
            // 需要查找的历史前缀和：
            // prefixSum - oldPrefix = k
            // 所以 oldPrefix = prefixSum - k
            if (map.containsKey(prefixSum - k)) {
                // 有多少个历史 oldPrefix，就能形成多少个以 r 结尾的合法子数组
                ans = ans + map.get(prefixSum - k);
            }
            // 当前 prefixSum 现在也成为“历史前缀和”
            // 如果以前出现过，就次数 +1；没出现过，就从 0 开始 +1
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }
        return ans;
    }

    /**
     * LC238：除自身以外数组的乘积（双数组版本）
     * 核心思想：
     * 对于每一个位置 i：
     * res[i] = nums[0..i-1] 的乘积 × nums[i+1..n-1] 的乘积
     * 即：
     * res[i] = left[i] × right[i]
     */
    public static int[] productExceptSelf(int[] nums) {

        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        int[] res = new int[nums.length];

        /**
         * left[i] 表示：
         * nums[0..i-1] 的乘积（不包含自己）
         * 为什么 left[0] = nums[0]？
         * 这里是为了构建滚动前缀结构（实现细节偏移处理）
         */
        left[0] = nums[0];

        /**
          right[i] 表示：
          nums[i+1..n-1] 的乘积（不包含自己）
          right[n-1] = 1 的原因：
          最右边元素右侧没有元素，空乘积定义为 1
         */
        right[nums.length - 1] = 1;

        /**
         * 构建左侧前缀乘积
         * left[i] = left[i-1] × nums[i-1]
         * 注意：这里 deliberately 不包含 nums[i]
         */
        for (int i = 1; i < nums.length; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }

        /**
         * 构建右侧后缀乘积
         * right[i] = right[i+1] × nums[i+1]
         * 同样：不包含 nums[i]
         */
        for (int i = nums.length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }

        /**
         * 重点！！！
         * 对于任意 i：
         * nums 被拆成三段：
         * [ 左边 | nums[i] | 右边 ]
         * left[i]  = 左边所有元素乘积
         * right[i] = 右边所有元素乘积
         * 因此：
         * res[i] = left[i] × right[i]
         * 为什么可以这样乘？
         * 因为乘法满足结合律：
         * (a × b × c) = (a × b) × (c)
         * nums[i] 被“刻意排除”，所以不参与计算
         */
        for (int i = 0; i < nums.length; i++) {
            res[i] = left[i] * right[i];
        }

        return res;
    }


    /**
     * LC238：O(1) 额外空间优化版本
     * 核心思想：
     * 不再显式构建 left[] 和 right[]
     * 而是：
     * 用两个“滚动变量”模拟左右乘积
     */
    public static int[] productExceptSelf2(int[] nums) {

        int left = 1;   // 左侧滚动乘积
        int right = 1;  // 右侧滚动乘积

        int[] res = new int[nums.length];

        // 初始化为 1（乘法单位元）
        Arrays.fill(res, 1);

        /**
         * 第一遍：从左往右
         * 此时 left 表示：
         * nums[0..i-1] 的乘积
         * 所以：
         * res[i] 先乘 left（左侧贡献）
         */
        for (int i = 0; i < nums.length; i++) {

            /**
             * ⭐ 为什么这里可以直接赋值 left？
             * 因为：
             * left = 当前 i 左边所有元素乘积
             */
            res[i] = left;

            // 更新 left，让 i 加入未来的左侧
            left *= nums[i];
        }

        /**
         * 第二遍：从右往左
         * right 表示：
         * nums[i+1..n-1] 的乘积
         */
        for (int i = nums.length - 1; i >= 0; i--) {

            /**
             * ⭐ 核心点：
             * res[i] 此时 = 左侧乘积
             * 再乘右侧乘积 right
             * 所以最终：
             * res[i] = left[i] × right[i]
             */
            res[i] *= right;

            // 更新 right，让 i 加入未来的右侧
            right *= nums[i];
        }

        return res;
    }

}