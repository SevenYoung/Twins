package sse.ustc.ds;

import java.util.Arrays;

/**
 * Created by SevenYoung on 15-6-1.
 */
public class MaxPriorityQueue<T extends Comparable<T>> {
    //Using the binary heap(complete tree with heap-order[big-end]) and array representation(without using the first element)
    private int N = 0;//the number of current elements
    private T[] data;//the data collection

    public MaxPriorityQueue(int capacity){
        data = (T[])new Comparable[capacity + 1];//without using data[0]
        N = 0;
    }


    public MaxPriorityQueue(T[] array, boolean forSort){
        //forSort represents whether this pq used for heap sort
        if(forSort == false){
            data = (T[])new Comparable[array.length + 1];//without using data[0]
            N = 0;
            for(T i: array)
                insert(i);
        }
        else{
            data = (T[])new Comparable[array.length + 1];//without using data[0],for heap sort
            for(int i=1; i<data.length; i++)
                data[i] = array[i-1];
            N = data.length - 1;

        }
    }


    public int size(){return N;}
    public boolean isEmpty(){return N==0;}

    //Return the max element
    public T max(){
        return data[1];
    }

    //Insert a item to this max priority queue
    public void insert(T item){
        data[++N] = item;
        up(N);
    }

    //bottom-up adjust the heap for keep big-end invariant
    private void up(int i){
        while (i > 1 && less(i/2,i)){
            exch(i,i/2);
            i = i/2;
        }
    }

    private void exch(int i,int j){
        T tmp = data[i]; data[i] = data[j]; data[j] = tmp;
    }

    private boolean less(int i, int j){return data[i].compareTo(data[j]) < 0;}

    public T delMax(){
        T max = data[1];
        exch(1,N--);
        data[N+1] = null;//avoid the loitering
        down(1);
        return max;
    }

    private void down(int i){
        while(i*2 <= N){
            int j = 2*i;
            if(j < N && less(j,j+1)) j++;//find out the larger child
            if(less(i,j)){exch(i,j); i = j;}//swap with larger child(smaller than this child)
            else break;
        }
    }

    public T[] heapsort(){
        for(int i=N/2; i>=1; i--)down(i);
        while(N>1){exch(1,N--);down(1);}
        T[] res = Arrays.copyOfRange(data,1,data.length);
        return res;
    }

    public String toString(){
        String res = "";
        for(T i:data){res+=" ";res+=i;}

        return res;
    }

//    public static void main(String[] args) {
//        Integer[] input = {23,1,52,2,4};
//        MaxPriorityQueue<Integer> mpq = new MaxPriorityQueue<>(3);
//        for(int i:input )
//        System.out.println(mpq);
//        while(!mpq.isEmpty())
//            System.out.println(mpq.delMax());

//        for(int i:input)
//            System.out.print(i + " ");
//        System.out.println();
//
//        Comparable[] out = mpq.heapsort();
//        for(Comparable i:out)
//            System.out.println(i);
//        System.out.println(mpq);
//
//    }

}
