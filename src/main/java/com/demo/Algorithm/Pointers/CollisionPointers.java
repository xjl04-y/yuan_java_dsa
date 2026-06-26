package com.demo.Algorithm.Pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 对撞指针
 */

public class CollisionPointers {

    /**
     * 力扣11. 盛水水的容器
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i])
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水
     * 返回容器可以储存的最大水量
     */
    public static int maxArea(int[] height) {
        // 物理边界指针：初始状态下底边宽度最大
        int left = 0;
        // 拉取二维搜索空间的最外围边界
        int right = height.length - 1;
        // 记录全局最大水桶容量（状态转移的极值）
        int max = 0;
        // 边界极值约束：必须严格小于。因为 left == right 时指针重合，底边宽度为 0，物理意义上无法构成容器，无意义的时钟周期消耗必须剪除
        while (left < right) {
            // O(1) 步长计算：基于连续内存的缓存亲和性，直接通过短板高度 * 物理宽度求当前面积
            int currentArea = Math.min(height[left], height[right]) * (right - left);
            if (max < currentArea) {
                // 更新全局最大水桶容量（状态转移的极值）
                max = currentArea;
            }
            // 数学降维与剪枝核心：水面高度的绝对上限由短板决定。移动长板收益概率绝对为 0%，因此必须且只能抛弃并移动短板
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }

    /**
     * 力扣167. 两数之和2 输入有序数组
     * 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
     * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
     * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
     * 你所设计的解决方案必须只使用常量级的额外空间。
     */
    public static int[] twoSum(int[] numbers, int target) {
        // 物理边界指针：初始状态下底边宽度最大
        int left = 0;
        // 拉取二维搜索空间的最外围边界
        int right = numbers.length - 1;
        int current = 0;
        while (left < right) {
            current = numbers[left] + numbers[right];
            if (current == target) {
                return new int[]{left + 1, right + 1};
            }
            if (current > target) {
                right--;
            } else {
                left++;
            }
        }
        return null;
    }

    /**
     * 力扣15. 三数之和
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
     * 注意：答案中不可以包含重复的三元组。
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        // 如果不是那么明白可以看threeSumsHtml这个文件
        // 存储最终不重复的三元组结果
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        // 1. 排序：这是对撞指针的前提，保证数组从小到大有序
        Arrays.sort(nums);

        // 2. 外层循环：遍历数组，依次把每个元素作为“固定值”
        for (int index = 0; index < nums.length; index++) {
            // 如果当前选的数字和上一次选的一模一样，直接跳过整个内部寻检
            if (index > 0 && nums[index] == nums[index - 1]) continue;
            // 3. 定义对撞指针：left从固定值的下一位开始，right从数组末尾开始
            int left = index + 1;
            int right = nums.length - 1;

            // 4. 内层循环：当left和right没有相遇时，不断寻找和为0的组合
            while (left < right) {
                int fixedValue = nums[index];
                int current = fixedValue + nums[left] + nums[right];

                if (current == 0) {
                    // 找到一组解，将其加入到结果列表中
                    int finalLeft = left;
                    int finalRight = right;
                    result.add(new ArrayList<Integer>() {{
                        add(fixedValue);
                        add(nums[finalLeft]);
                        add(nums[finalRight]);
                    }});
                    while (left < right && nums[left]==nums[left+1])left++; // 去重
                    while (left < right && nums[right]==nums[right-1])right--; //去重
                    // 找到解后，两个指针同时向中间收缩，继续寻找下一组解
                    left++;
                    right--;

                } else if (current < 0) {
                    // 注意这里改成了 else if！
                    // 如果是单纯的 if，当 current==0 执行完 left++ 和 right-- 后，
                    // 程序会继续往下判断。此时 current 变量里的值依然是 0，0 < 0 为 false，
                    // 就会进入 else 再次执行一次 right--，导致指针多移动了一次。

                    // 和太小，说明左侧的数字不够大，左指针右移
                    left++;
                } else {
                    // 和太大，说明右侧的数字不够小，右指针左移
                    right--;
                }
            }
        }

        return result;
    }
}

