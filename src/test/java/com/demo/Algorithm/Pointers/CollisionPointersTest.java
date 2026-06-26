package com.demo.Algorithm.Pointers;

import org.junit.jupiter.api.Test;

public class CollisionPointersTest {
    /**
     * 力扣11. 盛水水的容器
     * 给你 n 个非负整数，每个数代表坐标中的一个点，坐标在 (i, ai) 。
     * 在坐标系中画 n 条垂直线，使它们与 x 轴共同构成的容器可以容纳最多的水。
     */
    @Test
    public void TestMaxArea() {
        int[] height = {1,8,6,2,5,4,8,3,7};
        int max = CollisionPointers.maxArea(height);
        System.out.println(max);
    }

    /**
     * 力扣167. 两数之和2 输入有序数组
     * 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
     * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
     * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
     * 你所设计的解决方案必须只使用常量级的额外空间。
     */
    @Test
    public void TestTwoSum() {
        int[] numbers = {2,7,11,15};
        int target = 9;
        int[] result = CollisionPointers.twoSum(numbers, target);
        System.out.println(result[0] + " " + result[1]);


        int[] result2 = CollisionPointers.twoSum(new int[]{2,3,4}, 6);
        System.out.println(result2[0] + " " + result2[1]);
    }
    /**
     * 力扣15. 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
     * 请你找出所有满足为 0 的三元组。
     */
    @Test
    public void TestThreeSum() {
        int[] nums = {-1,0,1,2,-1,-4};
        System.out.println(CollisionPointers.threeSum(nums));
        //输出：[[-1,-1,2],[-1,0,1]]
    }
}
