package com.liuzq.uilts.linkedlist;

/*
https://www.cnblogs.com/Y-oung/p/8886142.html
 * 双端链表实现队列
 */
public class LinkedListToQueue {
    private LinkedListTwoPoint linkedlist;
    
    public LinkedListToQueue(){
        linkedlist = new LinkedListTwoPoint();
    }

    //队列大小
    public int size(){
        return linkedlist.size();
    }
    
    //是否为空队列
    public boolean isEmpty(){
        return linkedlist.isEmpty();
    }
    
    //入列，在链表表尾插入节点
    public void add(Object obj){
        linkedlist.insertInTail(obj);
    }
    
    //出列，在链表表头删除结点
    public Object poll(){
        if(this.isEmpty()) {
            return null;
        }
        return linkedlist.deleteHeadNode();
    }
    
    //查看队列头元素
    public Object peekHead(){
        if(this.isEmpty()) {
            return null;
        }
        return linkedlist.headNode();
    }
    
    //查看队列尾元素
    public Object peekTail(){
        if(this.isEmpty()) {
            return null;
        }
        return linkedlist.tailNode();
    }
    
    //打印队列元素
    public void display(){
        linkedlist.display();
    }
    
    public static void main(String[] args) {
        LinkedListToQueue stack = new LinkedListToQueue();
        stack.add(0);
        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.display();     //0<-1<-2<-3<-
        System.out.println(stack.peekHead()); //0
        System.out.println(stack.peekTail()); //3
        System.out.println(stack.poll());  //0
        System.out.println(stack.poll());  //1
        System.out.println(stack.poll());  //2
        System.out.println(stack.poll());  //3
        System.out.println(stack.poll());  //null
    }
}