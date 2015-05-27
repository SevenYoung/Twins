package cn.young.ds;

import java.util.Iterator;

/**
 * Created by SevenYoung on 15-5-27.
 */
public class LinkedStack<T> implements Iterable<T>{
    @Override
    public Iterator<T> iterator() {
        return (new LinkedStackIterator<T>());
    }

    private class LinkedStackIterator<T> implements Iterator<T> {
        @Override
        public boolean hasNext() {
            return !isEmpty();
        }

        @Override
        public T next() {
            return (T) pop();
        }

        @Override
        public void remove() {

        }
    }

    private class Node{
        T record;
        Node next;
        Node(T record,Node next){
            this.record = record;
            this.next = next;
        };
    }

    //The pointer always point to the top of the stack
    private Node top;
    private int size;

    public LinkedStack(){
        size = 0;
        top = null;
    }

    //Whether this stack is empty?
    public boolean isEmpty(){return size == 0;}

    //The number of items in this stack
    public int size(){return size;}

    //Push an item to the top of this stack
    public void push(T item){
        //If now the stack is empty,then this item is the first item
        if(isEmpty()){
            Node node = new Node(item,null);
            top = node;
            size++;
        }else{
            //There are items before
            Node node = new Node(item,top);
            top = node;
            size++;
        }
    }

    //Peek the top item without removing it
    public T peek(){return top.record;}

    public T pop(){
        Node tmp = top;
        T item = tmp.record;
        top = tmp.next;
        tmp = null;//for the garbage collection
        size--;
        return item;
    }

    //For the test
//    public static void main(String[] args) {
//        LinkedStack<Double> ls = new LinkedStack<>();
//        double[] input = {1.0,2.0,3.0,4.0,5.0};
//        for(double i: input){
//            ls.push(i);
//        }
//        System.out.printf("size: " + ls.size() + " " +
//                "IsEmpty: " + ls.isEmpty() + "\n");
//        for(double i:ls){
//            System.out.println(i);
//        }
//    }

}
