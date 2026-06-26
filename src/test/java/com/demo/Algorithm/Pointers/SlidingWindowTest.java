package com.demo.Algorithm.Pointers;

import org.junit.jupiter.api.Test;

public class SlidingWindowTest {

    /**
     * 209. 长度最小的子数组
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其总和大于等于 target 的长度最小的 子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     */
    @Test
    void testMinSubArrayLen(){
        int nums[] = {2,3,1,2,4,3};
        int target = 7;
        int minLen = SlidingWindow.minSubArrayLen(target, nums);
        System.out.println(minLen);
    }

    /**
     * 3. 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * TODO 学完哈希表再来
     */
    @Test
    void testLengthOfLongestSubstring(){
        String s = "abcabcbb";
        int maxLen = SlidingWindow.lengthOfLongestSubstring(s);
        System.out.println(maxLen);
    }

    /**
     * 1004. 最大连续1的个数III
     * 给你一个二进制数组 nums 和一个整数 k，你可以将 k 个值从 0 改为 1 。
     * 返回最大连续 1 的个数。
     */
    @Test
    void testLongestOnes(){
        int[] nums = {1,1,1,0,0,0,1,1,1,1};
        int k = 2;
        int maxLen = SlidingWindow.longestOnes(nums, k);
        System.out.println(maxLen);
    }

    @Test
    void testLongestSubarray(){
        int[] nums = {0,1,1,1,0,1,1,0,1};
        int maxLen = SlidingWindow.longestSubarray(nums);
        System.out.println(maxLen);
    }

}
