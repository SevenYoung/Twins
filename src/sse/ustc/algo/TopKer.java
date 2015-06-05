package sse.ustc.algo;
import sse.ustc.ds.MaxPriorityQueue;
import sse.ustc.ds.MinPriorityQueue;


/**
 * Created by SevenYoung on 15-6-1.
 */
public class TopKer<T extends Comparable<T>> {
    //Find out the top K elements in a finite space using priority queue
    private int k;
    private MinPriorityQueue<T> minPQ;
    private MaxPriorityQueue<T> maxPQ;

    public TopKer(int k){
        minPQ = new MinPriorityQueue<T>(k+1);
        maxPQ = new MaxPriorityQueue<T>(k+1);
        this.k = k;
    }

    public T[] topKMin(T[] input){
        if(input.length < k) return input;
        else{
            for(int i=0; i<k+1; i++)
                maxPQ.insert(input[i]);
            for(int i=k+1; i<input.length; i++){
                maxPQ.delMax();
                maxPQ.insert(input[i]);
            }
            maxPQ.delMax();
            T[] res = (T[]) new Comparable[k];
            for(int i=0; i<k; i++)
                res[i] = maxPQ.delMax();
            return res;
        }

    }

    public T[] topKMax(T[] input){
        if(input.length < k) return input;
        else{
            for(int i=0; i<k+1; i++)
                minPQ.insert(input[i]);
            for(int i=k+1; i<input.length; i++){
                minPQ.delMin();
                minPQ.insert(input[i]);
            }
            minPQ.delMin();
            T[] res = (T[]) new Comparable[k];
            for(int i=0; i<k; i++)
                res[i] = minPQ.delMin();
            return res;
        }

    }

    public static void main(String[] args) {
        Integer[] input = {23,1,53,12,14,34};
        TopKer<Integer> tp = new TopKer<>(4);
        Comparable[] out1 = tp.topKMax(input);
        for(Comparable i: out1)
            System.out.printf(i + " ");
        System.out.println();
        Comparable[] out2 = tp.topKMin(input);
        for(Comparable i: out2)
            System.out.printf(i + " ");


    }




}
