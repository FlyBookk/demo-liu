package com.liuzq.uilts.linkedlist;

/*
 * https://www.cnblogs.com/Y-oung/p/8886142.html
 * 双向链表
 * 单向链表只可向一个方向遍历，一般查找一个结点的时候需要从第一个结点开始每次访问下一个结点，一直访问到需要的位置。
 * 双向链表的每个结点都有指向的前一个结点和后一个节点，既有链表表头又有表尾，即可从链头向链尾遍历，又可从链尾向链头遍历。
 * LinkedList中的私有静态内部类Node实际上就是一个双向链表，<E>代表泛型，指明结点的数据类型
 * private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
 */
public class LinkedListTwoDirections {
    private Node head;  //头结点
    private Node tail;  //尾结点
    private int size;  //链表长度，即链表中结点数量
    
    public LinkedListTwoDirections(){
        head = null;
        tail = null;
        size = 0;
    }
    
    //私有内部类，代表链表每个结点
    private class Node{
        private Object data;    //链表结点的值
        private Node previous;  //当前结点指向的前一个结点
        private Node next;      //当前结点指向的下一个结点
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
            head.previous = newNode;
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
            newNode.previous = tail;
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
            head.previous = null;
        }
        size--;
        return obj;
    }
    
    //删除链表表尾结点
    public Object deleteTailNode(){
        if(size == 0) return null;
        Object obj = tail.data;
        if(tail.previous == null){  //只有一个结点
            head = null;
            tail = null;
        }else{
            tail = tail.previous;
            tail.next = null;
        }
        size--;
        return obj;
    }
    
    //正向打印链表
    public void display(){
        if(size == 0) System.out.println("链表为空！");
        Node n = head;
        while(n != null){
            System.out.print("<-"+n.data);
            n = n.next;
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        LinkedListTwoDirections list = new LinkedListTwoDirections();
        System.out.println(list.isEmpty());            //true
        System.out.println(list.size());               //0
        list.display();                                //链表为空！
        
        list.insertInHead(0);
        list.insertInHead(1);
        list.insertInHead(2);
        list.insertInHead(3);        
        list.display();                                //<-3<-2<-1<-0
        System.out.println(list.deleteHeadNode());     //3
        
        list.insertInTail(1);
        list.insertInTail(2);
        list.display();                                //<-2<-1<-0<-1<-2
        
    }
}