package com.liuzq.bean.linkedlist;

import java.util.Stack;

/**
 * https://www.cnblogs.com/Y-oung/p/8886142.html
 * 双端链表，比单链表多了个尾结点
 */
public class LinkedListTwoPoint {
    private Node head;  //头结点
    private Node tail;  //尾结点
    private int size;  //链表长度，即链表中结点数量
    
    public LinkedListTwoPoint(){
        head = null;  //头结点
        tail = null;  //尾结点
        size = 0;  //链表长度，即链表中结点数量
    }
    
    //私有内部类，代表链表每个结点
    private class Node{
        private Object data;  //链表结点的值
        private Node next;  //指向的下一个结点
        public Node(Object data){
            this.data = data;
        }
    }
    
    //判断链表是否为空
    public boolean isEmpty(){
        return size==0?true:false;
    }
    
    //返回链表长度
    public int size(){
        return size;
    }
    
    //查看链表头结点，不移除
    public Object headNode(){
        if(size == 0) return null;
        return head.data;
    }
    
    //查看链表尾结点，不移除
    public Object tailNode(){
        if(size == 0) return null;
        return tail.data;
    }
    
    //在链表表头插入一个结点
    public void insertInHead(Object obj){
        Node newNode = new Node(obj);
        if(size == 0){
            head = newNode;
            tail = newNode;
        }else{
            newNode.next = head;
            head = newNode;
        }
        size++;
    }
    //在链表表尾插入一个结点
    public void insertInTail(Object obj){
        Node newNode = new Node(obj);
        if(size == 0){
            head = newNode;
            tail = newNode;
        }else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    
    //删除链表表头结点
    public Object deleteHeadNode(){
        if(size == 0) return null;
        Object obj = head.data;
        if(head.next == null){  //只有一个结点
            head = null;
            tail = null;
        }else{
            head = head.next;
        }
        size--;
        return obj;
    }
    
    //链表查找：判断链表中是否包含某个元素
    public boolean containObject(Object obj){
        if(size == 0) return false;
        Node n = head;
        while(n != null){
            if(n.data == obj) return true;
            else n = n.next;
        }
        return false;
    }
    
    //删除链表中的指定结点（如果包含多个指定结点，只会删除第一个）
    public boolean deleteNode(Object obj){
        if(size == 0){
            System.out.println("链表为空！");
            return false;
        }
        //先在链表中查询是否包含该结点，找到之后获取该结点和其前一个结点
        Node previous = null;  //前一个结点
        Node current = head;  //当前结点
        while(current.data != obj){
            if(current.next == null){
                System.out.println("没有找到该结点！");
                return false;
            }
            previous = current;
            current = current.next;
        }
        if(current == head){
            this.deleteHeadNode();
        }else{
            previous.next = current.next;
            size--;
        }
        return true;
    }
    
    //正向打印链表
    public void display(){
        if(size == 0) System.out.println("链表为空！");
        Node n = head;
        while(n != null){
            System.out.print(n.data + "<-");
            n = n.next;
        }
        System.out.println();
    }
    
    //反向打印链表（用栈）
    public void printListFromTailToHead(Node node){
        if(node == null) System.out.println("链表为空！");
        Stack<Integer> sta = new Stack<Integer>();
        while(node != null){
            sta.push((Integer) node.data);  //先将链表压入栈中
            node = node.next;
        }
        while(!sta.empty()){
            System.out.print(sta.pop()+"<-");  //出栈
        }
        System.out.println();
    }
    
    //反向打印链表（递归）
    public void printListFromTailToHeadDiGui(Node node){
        if(node == null){
            System.out.println("链表为空！");
        }else{
            if(node.next != null){
                printListFromTailToHeadDiGui(node.next);
            }
            System.out.print(node.data+"<-");
        }
    }
    
    
    public static void main(String[] args) {
        LinkedListTwoPoint list = new LinkedListTwoPoint();
        System.out.println(list.isEmpty());            //true
        System.out.println(list.size());               //0
        list.display();                                //链表为空！
        list.printListFromTailToHead(list.head);       //链表为空！
        
        list.insertInHead(0);
        list.insertInHead(1);
        list.insertInHead(2);
        list.insertInHead(3);        
        list.display();                                //3<-2<-1<-0<-
        list.printListFromTailToHead(list.head);       //0<-1<-2<-3<-
        list.printListFromTailToHeadDiGui(list.head);  //0<-1<-2<-3<-
        System.out.println(list.isEmpty());            //false
        System.out.println(list.size());               //4
        System.out.println(list.containObject(1));     //true
    }
}