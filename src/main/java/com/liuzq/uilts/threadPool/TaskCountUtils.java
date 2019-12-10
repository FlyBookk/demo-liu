package com.liuzq.uilts.threadPool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: TaskCountUtils
 * @Author: liuzq
 * @Date: 2019/10/24 16:17
 * @Version: 1.0
 */
public class TaskCountUtils {
   private  static AtomicInteger count = new AtomicInteger(0);

    public static AtomicInteger getCount() {
        return count;
    }
    /*
    * 设置记录值为当前传入值
    * */
    public static void setCount(int count) {
        TaskCountUtils.count.addAndGet(count);
    }

    /*
    * 增加任务1
    * */
    public static int increment() {
        return count.incrementAndGet();
    }
    /*
    * 任务数减1
    * */
    public static int decrement() {
        return count.decrementAndGet();
    }


}
