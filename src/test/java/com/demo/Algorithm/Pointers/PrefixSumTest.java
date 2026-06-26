package com.demo.Algorithm.Pointers;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class PrefixSumTest {

    @Test
    void TestSumRange() {

        int[] nums = {1, 2, 3, 4, 5};
        PrefixSum.NumArray numArray = new PrefixSum.NumArray(nums);
        // 测试 1：普通区间
        System.out.println("sum(1,3) = " + numArray.sumRange(1, 3));
        // 2+3+4 = 9

        // 测试 2：从 0 开始
        System.out.println("sum(0,2) = " + numArray.sumRange(0, 2));
        // 1+2+3 = 6

        // 测试 3：单点
        System.out.println("sum(3,3) = " + numArray.sumRange(3, 3));
        // 4

        // 测试 4：整个数组
        System.out.println("sum(0,4) = " + numArray.sumRange(0, 4));
        // 15
    }

    @Test
    void TestSubarraySum(){
        int[] nums = {1,1,1};
        System.out.println(PrefixSum.subarraySum(nums, 2));
        int[] nums2 = {0,0,0};
        System.out.println(PrefixSum.subarraySum(nums2, 0));
    }

    @Test
    void TestPivotIndex(){
        int[] nums = {1, 7, 3, 6, 5, 6};
        System.out.println(PrefixSum.pivotIndex(nums));
    }

    @Test
    void TestProductExceptSelf(){
        int[] nums = {1, 2, 3, 4};
        System.out.println(Arrays.toString(PrefixSum.productExceptSelf(nums)));
    }

    @Test
    void TestProductExceptSelf2(){
        int[] nums = {1, 2, 3, 4};
        System.out.println(Arrays.toString(PrefixSum.productExceptSelf2(nums)));
    }
}