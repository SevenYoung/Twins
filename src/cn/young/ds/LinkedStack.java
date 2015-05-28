package cn.young.ds;

import java.util.Iterator;
import cn.young.ds.LinkedList;

/**
 * Created by SevenYoung on 15-5-27.
 */
public class LinkedStack<T> extends LinkedList<T> implements Iterable<T>{
    @Override
    public Iterator<T> iterator() {
        return (new LinkedStackIterator<T>());
    }

    private class LinkedStackIterator<T> implements Iterator<T> {
        Node tmp = getFirst();
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

    //Push an item to the top of this stack
    public void push(T item){
        insertFirst(item);
    }

    //Peek the top item without removing it
    public T peek(){return peekFirst();}

    public T pop(){
        return removeFirst();
    }

//    //For the test
//    public static void main(String[] args) {
//        LinkedStack<Double> ls = new LinkedStack<>();
//        double[] input = {1.0,2.0,3.0,4.0,5.0};
//        for(double i: input){
//            ls.push(i);
//        }
//        ls.pop();
//        System.out.printf("size: " + ls.size() + " " +
//                "IsEmpty: " + ls.isEmpty() + "\n");
//        for(double i:ls){
//            System.out.println(i);
//        }
//        System.out.printf("size: " + ls.size() + " " +
//                "IsEmpty: " + ls.isEmpty() + "\n");
//        ls.pop();
//        for(double i:ls){
//            System.out.println(i);
//        }
//
//    }

}
