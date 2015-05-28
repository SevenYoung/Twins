package cn.young.ds;

/**
 * Created by SevenYoung on 15-5-28.
 */
public class LinkedQueue<T> extends LinkedList<T> {
    //LinkedQueue is-a LinkedList

    public void enqueue(T item){insertLast(item);}

    public T dequeue(){return removeFirst();}

    public T first(){return peekFirst();}

    public T last(){return peekLast();}

}
