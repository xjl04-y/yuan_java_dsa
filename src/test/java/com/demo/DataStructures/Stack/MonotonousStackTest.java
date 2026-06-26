package com.demo.DataStructures.Stack;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MonotonousStackTest {

    @Test
    void TestDailyTemperatures(){
        int[] temperatures = new int[]{73,74,75,71,69,72,76,73};
        System.out.println(Arrays.toString(MonotonousStack.dailyTemperatures(temperatures)));

    }

    @Test
    void  TestNextGreaterElement(){
        int[] nums1 = new int[]{4, 1, 2};
        int[] nums2 = new int[]{1, 3, 4, 2};
        MonotonousStack.nextGreaterElement(nums1,nums2);
    }


}
