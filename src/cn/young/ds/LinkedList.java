package cn.young.ds;

import java.util.Iterator;

/**
 * Created by SevenYoung on 15-5-27.
 */
//This class is the superclass of LinkedQueue and LinkedList
public class LinkedList<T> implements Iterable<T>{
    @Override
    public Iterator<T> iterator() {
        return (new LinkedListIterator<T>());
    }

    private class LinkedListIterator<T> implements Iterator<T> {
        Node tmp = first;
        @Override
        public boolean hasNext() {
            return tmp != null;
        }

        @Override
        public T next() {
            T res =  (T) tmp.record;
            tmp = tmp.next;
            return res;
        }

        @Override
        public void remove() {

        }
    }

    public class Node{
        T record;
        Node next;
        Node(T record,Node next){
            this.record = record;
            this.next = next;
        };
    }

    private Node first;
    private int size;
    private Node last;

    public LinkedList(){
        size = 0;
        first = null;
        last = null;
    }

    public Node getFirst(){
        return first;
    }
    //Whether this list is empty?
    public boolean isEmpty(){return size == 0;}

    //The number of items in this list
    public int size(){return size;}

    //insert an item to the first of this list
    public void insertFirst(T item){
        //If now the list is empty,then this item is the first item
        if(isEmpty()){
            Node node = new Node(item,null);
            first = node;
            last = node;
            size++;
        }else{
            //There are items before
            Node node = new Node(item,first);
            first = node;
            size++;
        }
    }
    //remove an item from the fist of this list
    public T removeFirst(){
        Node tmp = first;
        if(tmp == null)return null;
        T item = tmp.record;
        first = tmp.next;
        if(isEmpty()){
            last = null;
        }
        tmp = null;//for the garbage collection
        size--;
        return item;

    }

    public void insertLast(T item){
        //If now the list is empty,then this item is the first item
        if(isEmpty()){
            Node node = new Node(item,null);
            first = node;
            last = node;
            size++;
        }else{
            //There are items before
            Node node = new Node(item,null);
            last.next = node;
            last = node;
            size++;
        }
    }

    public T removeLast(){
        Node tmp = last;
        //If there is no elements in this list,return null
        if(tmp == null) return null;
        else if(tmp == first) {
            //only one element in this list
            T item = tmp.record;
            first = last = null;
            tmp = null;//for the garbage collection
            size --;
            return item;
        }
        else {
            //at least two elements in this list
            T item = tmp.record;
            Node i = null;
            for (i = first; i.next != last; i = i.next) ;
            last = i;
            last.next = null;
            tmp = null;//for the garbage collection
            size--;
            return item;
        }
    }




    //Peek the first item without removing it
    public T peekFirst(){return first.record;}
    public T peekLast(){return last.record;}



    //For the test
//    public static void main(String[] args) {
//        LinkedList<Integer> ll = new LinkedList<>();
//        int[] input = {1,2,3,4,5,6};
//        for(int i:input)
//            ll.insertLast(i);
//        ll.removeFirst();
//        ll.removeLast();
//        for(int i:ll)
//            System.out.println(i);
//
//    }

}
