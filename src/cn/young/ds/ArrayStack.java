package cn.young.ds;

import java.util.Iterator;

/**
 * Created by SevenYoung on 15-5-28.
 */
public class ArrayStack <T> implements Iterable<T>{
    private T[] items;      //collect the data
    private int N;          //the number of the items
    private int initialSize;//initial array size
    private int threshold;   //when the N <= threshold, we can not resize
    public ArrayStack(){
        //default the initialSize==16, threshold==128
        this(16,128);
    }
    public ArrayStack(int initialSize, int threshold){
        this.initialSize = initialSize;
        this.threshold = threshold;
        items = (T[]) new Object[initialSize];
        this.N = 0;
    }

    public boolean isEmpty(){return N == 0;}
    public int size(){return N;}

    private void resize(int newSize){
        T[] newArray = (T[]) new Object[newSize];
        for(int i=0; i < items.length; i++)
            newArray[i] = items[i];
        items = newArray;
    }

    public void push(T item){
        if(N == items.length)
            resize(2 * items.length);
        items[N++] = item;
    }

    public T pop(){
        T res = items[--N];
        items[N] = null;//avoid loitering(explicitly set the unused item to null for garbage collection)
        if(N >= threshold && N < items.length/4){
            //when the number of items large than threshold and less than the 1/4 * current length
            resize(items.length/2);
        }
        return res;
    }

    public T peek(){
        return items[N-1];
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayStackIterator<T>();
    }

    private class ArrayStackIterator<T> implements Iterator<T>{
        int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public T next() {
            return (T)items[--i];
        }

        @Override
        public void remove() {

        }
    }

    //main for testing
//    public static void main(String[] args) {
//        ArrayStack<Integer> as = new ArrayStack<>();
//        int[] input = {1,2,3,4,5,6,23};
//        for(int i:input)
//            as.push(i);
//        System.out.println("Size:" + as.size());
//        for(int i:as){
//            System.out.println(i);
//        }
//        System.out.println("Size:" + as.size());
//
//
//    }
}
