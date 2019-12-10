package com.liuzq.leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: springboot-liuzq
 * @Package: com.liuzq.leetcode
 * @ClassName: TwoNumSum
 * @Author: liuzq
 * @Description: 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * @Date: 2019/12/6 17:11
 * @Version: 1.0
 */
public class TwoNumSum {

    public static void main(String[] args) {
        List ints = twoSum(new int[]{2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15}, 17);
        int[] ints2 = twoSum1(new int[]{2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15}, 17);
        System.out.println(JSON.toJSONString(ints));
        System.out.println(JSON.toJSONString(ints2));
    }

    /*
     *
     * 如果要找出数组中所有满足此条件的元素,只需要用List<int[]>接收即可
     * */
    public static List<int[]> twoSum(int[] nums, int target) {
        List<int[]> list = new ArrayList<>();
        /*
         * a+b=target
         * */
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int b = nums[j];
                if (a + b == target) {
                    int[] result = new int[2];
                    result[0] = i;
                    result[1] = j;
                    list.add(result);
                }
            }
        }
        return list;
    }

    public static int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
