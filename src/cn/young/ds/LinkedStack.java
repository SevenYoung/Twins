package cn.young.ds;

/**
 * Created by SevenYoung on 15-5-27.
 */
public class LinkedStack<T> extends LinkedList<T> {
    //LinkedStack is-a LinkedList!

    public void push(T item){insertFirst(item);}

    //Peek the top item without removing it
    public T peek(){return peekFirst();}

    public T pop(){return removeFirst();}
}
