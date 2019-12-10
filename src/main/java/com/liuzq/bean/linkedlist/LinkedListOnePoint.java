package com.liuzq.bean.linkedlist;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * https://www.cnblogs.com/Y-oung/p/8886142.html
 * 单向链表
 */
public class LinkedListOnePoint {
    private Node head;  //头结点
    private int size;  //链表长度，即链表中结点数量

    public LinkedListOnePoint() {
        head = null;
        size = 0;
    }

    //私有内部类，代表链表每个结点
    private class Node {
        private Object data;  //链表结点的值
        private Node next;  //指向的下一个结点

        public Node(Object data) {
            this.data = data;
        }
    }

    //判断链表是否为空
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    //返回链表长度
    public int size() {
        return size;
    }

    //查看链表头结点，不移除
    public Object headNode() {
        if (size == 0) return null;
        return head.data;
    }

    //在链表表头插入一个结点（入栈）
    public void insertInHead(Object obj) {
        Node newNode = new Node(obj);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    //删除链表表头结点（出栈）
    public Object deleteHeadNode() {
        if (size == 0) {
            return null;
        }
        Object obj = head.data;
        if (head.next == null) {
            head = null;  //只有一个结点
        } else {
            head = head.next;
        }
        size--;
        return obj;
    }

    //链表查找：判断链表中是否包含某个元素
    public boolean containObject(Object obj) {
        if (size == 0) {
            return false;
        }
        Node n = head;
        while (n != null) {
            if (n.data == obj) {
                return true;
            } else {
                n = n.next;
            }
        }
        return false;
    }

    //删除链表中的指定结点（如果包含多个指定结点，只会删除第一个）
    public boolean deleteNode(Object obj) {
        if (size == 0) {
            System.out.println("链表为空！");
            return false;
        }
        //先在链表中查询是否包含该结点，找到之后获取该结点和其前一个结点
        Node previous = null;  //前一个结点
        Node current = head;  //当前结点
        while (current.data != obj) {
            if (current.next == null) {
                System.out.println("没有找到该结点！");
                return false;
            }
            previous = current;
            current = current.next;
        }
        if (current == head) {
            this.deleteHeadNode();
        } else {
            previous.next = current.next;
            size--;
        }
        return true;
    }

    //正向打印链表
    public void display() {
        if (size == 0) System.out.println("链表为空！");
        Node n = head;
        while (n != null) {
            System.out.print("<-" + n.data);
            n = n.next;
        }
        System.out.println();
    }

    //反向打印链表（用栈）
    public void printListFromTailToHead(Node node) {
        if (node == null) System.out.println("链表为空！");
        Stack<Integer> sta = new Stack<Integer>();
        while (node != null) {
            sta.push((Integer) node.data);  //先将链表压入栈中
            node = node.next;
        }
        while (!sta.empty()) {
            System.out.print(sta.pop() + "<-");  //出栈
        }
        System.out.println();
    }

    //反向打印链表（递归）
    public void printListFromTailToHeadDiGui(Node node) {
        if (node == null) {
            System.out.println("链表为空！");
        } else {
            if (node.next != null) {
                printListFromTailToHeadDiGui(node.next);
            }
            System.out.print(node.data + "<-");
        }
    }


    public static void main(String[] args) {
        int[] ints = twoSum1(new int[]{2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15}, 17);
        System.out.println(JSON.toJSONString(ints));
        LinkedListOnePoint list = new LinkedListOnePoint();
        System.out.println(list.isEmpty());            //true
        System.out.println(list.size());               //0
        list.display();                                //链表为空！
        list.printListFromTailToHead(list.head);       //链表为空！
        list.insertInHead(0);
        list.insertInHead(1);
        list.insertInHead(2);
        list.insertInHead(3);
        list.display();                                //<-3<-2<-1<-0
        list.printListFromTailToHead(list.head);       //0<-1<-2<-3<-
        list.printListFromTailToHeadDiGui(list.head);  //0<-1<-2<-3<-
        System.out.println(list.isEmpty());            //false
        System.out.println(list.size());               //4
        System.out.println(list.containObject(1));     //true

        List<int[]> ints1 = twoSum(new int[]{2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 152, 7, 11, 15, 2, 7, 11, 15, 2, 7, 11, 15}, 17);
    }
    /*
    * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
    * 如果要找出数组中所有满足此条件的元素,只需要用List<int[]>接收即可
    * */
    public static List<int[]> twoSum(int[] nums, int target) {
        List<int[]> list=new ArrayList<>();
        /*
         * a+b=target
         * */
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i];
            for (int j = i+1; j < nums.length; j++) {
                int b = nums[j];
                if (a + b == target) {
                int[] result = new int[2];
                    result[0]=i;
                    result[1]=j;
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
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}