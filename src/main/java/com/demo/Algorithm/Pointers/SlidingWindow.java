package com.demo.Algorithm.Pointers;

public class SlidingWindow {

    /**
     * 209. 长度最小的子数组
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其总和大于等于 target 的长度最小的 子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     */
    public static int minSubArrayLen(int target, int[] nums) {
        // 阶段一 & 三：定义状态空间。左指针，代表窗口的左边缘
        int left = 0;
        // 窗口内元素的总和，随着窗口的滑动动态更新
        int windowSum = 0;
        // 记录历史遇到的最小合法窗口长度。初始值设为极大值，作为“未找到”的兜底标志
        int minLen = Integer.MAX_VALUE;

        // 阶段三：窗口扩张（外层逻辑）。
        // 右指针 right 充当探路者，一路向右滑动（顺序访问，对 CPU 缓存极度友好）
        for (int right = 0; right < nums.length; right++) {
            // 动作 1：右边缘“吃进”新元素，扩大窗口
            windowSum += nums[right];

            // 动作 2：窗口收缩（内层逻辑）。
            // 警报 A：必须使用 while 循环！只要窗口和依然满足条件，就持续“吐出”左边元素，榨干每一滴最小长度的潜力
            while (windowSum >= target) {
                // 警报 B：计算当前合法窗口的真实物理长度（左右边界都包含，所以需要 + 1）
                int curLen = right - left + 1;

                // 更新历史最小长度
                if (curLen < minLen) {
                    minLen = curLen;
                }

                // 动作 3：左边缘“吐出”最左侧的旧元素
                windowSum -= nums[left];
                // 左指针向右逼近（绝不回头），寻找更短的潜在合法解
                left++;
            }
        }

        // 警报 C：系统兜底机制。
        // 如果遍历完全部元素，minLen 依然是初始的极大值，说明不存在任何符合条件的子数组，返回 0
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * 3. 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     */
    public static int lengthOfLongestSubstring(String s) {
        // TODO 学完哈希表之后在回来写
        int left = 0;
        int maxLen = 0;
        char[] chars = s.toCharArray();
        return maxLen;
    }


    /**
     * 1004. 最大连续1的个数 III
     * 给定一个由若干 0 和 1 组成的数组 nums 和一个整数 k。
     * 你可以最多将 k 个 0 变成 1，返回执行上述操作后，数组中包含的最多连续 1 的最大个数。
     */
    public static int longestOnes(int[] nums, int k) {
        int left = 0;
        int maxLen = 0; // 历史最高分，只能增加不能减少
        int zeroCount = 0; // 账本：记录当前框里有多少个 0

        // 阶段三：外层循环，右边缘 right 作为探路者，不断吃进新元素
        for (int right = 0; right < nums.length; right++) {
            // 动作 1：如果吃进的是 0，账本记上一笔
            if (nums[right] == 0) {
                zeroCount++;
            }

            // 动作 2：内层收缩逻辑。警报 🚨：当框内的 0 超过了最大容忍度 k 时
            while (zeroCount > k) {
                // 左边缘准备吐出元素，如果吐出的恰好是 0，账本减一
                if (nums[left] == 0) {
                    zeroCount--;
                }
                // 左边缘向右逼近，缩小窗口以恢复合法状态
                left++;
            }

            // 动作 3：此时窗口一定合法（0 的数量 <= k）。
            // 气定神闲地按下快门 📸，用当前真实的物理长度 (right - left + 1) 去刷新历史最高分
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    /**
     * 1493. 删掉一个元素以后全为 1 的最长子数组
     * 给你一个二进制数组 nums ，你需要从中删掉一个元素。
     * 请你在删掉元素的结果数组中，返回最长的且只包含 1 的非空子数组的长度。
     * 如果不存在这样的子数组，请返回 0 。
     */
    public static int longestSubarray(int[] nums) {
        int left = 0;
        int maxLen = 0;
        int zeroCount = 0;
        for (int right = 0; right < nums.length; right++) {
            if(nums[right] == 0){
                zeroCount++;
            }
            while (zeroCount > 1){
                if(nums[left] == 0){
                    zeroCount--;
                }
                left++;
            }
            maxLen = Math.max(maxLen, right - left);
        }
        return maxLen;
    }

}
