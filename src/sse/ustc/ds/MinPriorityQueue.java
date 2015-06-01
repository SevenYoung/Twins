package sse.ustc.ds;

/**
 * Created by SevenYoung on 15-6-1.
 */
public class MinPriorityQueue <T extends Comparable<T>>{
    //Using the binary heap(complete tree with heap-order[big-end]) and array representation(without using the first element)
    private int N = 0;//the number of current elements
    private T[] data;//the data collection

    MinPriorityQueue(int capacity){
        data = (T[])new Comparable[capacity + 1];//without using data[0]
        N = 0;
    }

    MinPriorityQueue(T[] array){
        data = (T[])new Comparable[array.length + 1];//without using data[0]
        N = 0;
        for(T i: array)
            insert(i);
    }

    public int size(){return N;}
    public boolean isEmpty(){return N==0;}

    //Return the max element
    public T min(){
        return data[1];
    }

    //Insert a item to this max priority queue
    public void insert(T item){
        data[++N] = item;
        up(N);
    }

    //bottom-up adjust the heap for keep big-end invariant
    private void up(int i){
        while (i > 1 && great(i / 2, i)){
            exch(i,i/2);
            i = i/2;
        }
    }

    private void exch(int i,int j){
        T tmp = data[i]; data[i] = data[j]; data[j] = tmp;
    }

    private boolean great(int i, int j){return data[i].compareTo(data[j]) > 0;}

    public T delMin(){
        T max = data[1];
        exch(1,N--);
        data[N+1] = null;//avoid the loitering
        down(1);
        return max;
    }

    private void down(int i){
        while(i*2 <= N){
            int j = 2*i;
            if(j < N && great(j, j + 1)) j++;//find out the smaller child
            if(great(i, j)){exch(i,j); i = j;}//swap with larger child(smaller than this child)
            else break;
        }
    }

    public static void main(String[] args) {
        Integer[] input = {23,1,52,2,4};
        MinPriorityQueue<Integer> mpq = new MinPriorityQueue<Integer>(input);
        System.out.println(mpq.size());
        while(!mpq.isEmpty()){
            System.out.println(mpq.delMin());
        }
    }
}
