package com.liuzq.bean.linkedlist;

/**
 * https://www.cnblogs.com/Y-oung/p/8886142.html
 * 单链表实现栈
 */
public class LinkedListToStack {
    private LinkedListOnePoint linkedlist;
    
    public LinkedListToStack(){
        linkedlist = new LinkedListOnePoint();
    }

    //栈大小
    public int size(){
        return linkedlist.size();
    }
    
    //是否为空栈
    public boolean isEmpty(){
        return linkedlist.isEmpty();
    }
    
    //入栈
    public void push(Object obj){
        linkedlist.insertInHead(obj);
    }
    
    //出栈
    public Object pop(){
        if(this.isEmpty()) return null;
        return linkedlist.deleteHeadNode();
    }
    
    //查看栈顶元素
    public Object peek(){
        if(this.isEmpty()) return null;
        return linkedlist.headNode();
    }
    
    //打印栈中元素
    public void display(){
        linkedlist.display();
    }
    
    public static void main(String[] args) {
        LinkedListToStack stack = new LinkedListToStack();
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.display();     //<-3<-2<-1<-0
        System.out.println(stack.peek()); //3
        System.out.println(stack.pop());  //3
        System.out.println(stack.pop());  //2
        System.out.println(stack.pop());  //1
        System.out.println(stack.pop());  //0
        System.out.println(stack.pop());  //null
    }
}