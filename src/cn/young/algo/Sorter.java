package cn.young.algo;

/**
 * Created by SevenYoung on 15-5-29.
 */
public class Sorter<T extends Comparable<T>> {
    //This class just like a worker whose duty is sorting the elements it collects
    //It has many sort methods,elementary sorts(Insert sort,Bubble sort,Selection sort),Shell sort,Quick sort,Merge sort,Heap sort and so on.
    //Diff sort has diff property, you will need to choose one in the specific circumstance.

    private T[] data;//The data need to be sorted
    private static Comparable[] aux;//extra space used for merge
    Sorter(T[] data){this.data = data;}

    public void collectData(T[] data){
        this.data = data;
    }

    private boolean less(T a,T b){
        if(a.compareTo(b) < 0) return true;
        else return false;
    }

    private void exch(T[] data, int i, int j){
        //four accesses
        T tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    //Selection Sort and its properties:
    //(1)N^2/2 compares and N exchanges
    //(2)Insensitive to input
    //(3)Data movement is minimal
    public T[] selectionSort(){
        for(int i=0; i<data.length; i++){
            int min = i;
            for(int j=i; j<data.length; j++){
                if(less(data[j],data[min])) min = j;
            }
            exch(data,i,min);
        }
        return data;
    }

    //Insertion Sort and its properties:
    //(1)N^2/4 compares and N^2/4 exchanges
    //(2)Sensitive to the input,the best case only need N-1 compares and 0 exchanges, the worst, N^2/2 compares and N^2/2 exchanges
    //(3)Suit for partially sorted data collection.
    //It's a excellent method for partially sorted arrays and is fine method in tiny array, it's important in advanced algorithm too.
    private void insertionSortIml(T[] data,int lo, int hi){
        //It seems to pick the poker .
        for(int i=lo+1; i<=hi; i++){
            //Begin with second poker
            T tmp = data[i];
            int j;//record the insert position
            //Every larger element moves to right one step.
            //In fact,there is another method,tmp exchange with every encountering larger element.(bubble?)
            for(j=i-1; j>=lo ; j--)
                if(less(tmp,data[j]))
                    data[j+1] = data[j];
                else break;//The sequence before this element are all less than current element
            data[j+1] = tmp;
        }
//        return data;
    }

    public T[] insertionSort(){
        insertionSortIml(data,0,data.length-1);
        return data;
    }



    //A magic sort method based on the insertionSort using the partial order
    public T[] shellSort(){
        int N = data.length;
        int gap = 1;
        while(gap < N/3) gap = gap * 3 + 1;//knuth gap....  3^k
        while (gap >= 1){
            //Start at the biggest gap,the element with this gap far from the first element and elements after this should be compared!
            for(int i=gap; i<data.length; i++){
                //subSequence sorted by insertionSort
                for(int j=i; j>=gap; j-=gap)
                    if(less(data[j],data[j-gap]))
                        exch(data,j,j-gap);
            }
            gap /= 3;//shrink the gap
        }
        return data;
    }

    //Merge method in the mergeSort, in-place sort with extra space
    private void merge(T[] data, int lo, int mid, int hi){
        for(int i=lo; i<=hi; i++)
            aux[i] = data[i];
        //Merge data[lo,mid] and data[mid+1,hi]
        int left = lo;
        int right = mid + 1;
        for(int i=lo; i<=hi; i++){
            if(left > mid) data[i] = (T)aux[right++];//lest is exhausted
            else if(right > hi) data[i] = (T)aux[left++];//right is exhauste
            else if(less((T)aux[left],(T)aux[right])) data[i] = (T)aux[left++];
            else data[i] = (T)aux[right++];
        }
    }

    public T[] mergeSort(){
        mergeSortIml(data, 0, data.length - 1);
        return data;
    }

    public T[] mergeSort(int lo,int hi){
        mergeSortIml(data,lo,hi);
        return data;
    }

    private void mergeSortIml(T[] data,int lo, int hi){
        //Using the Top-down way, divide the large array to small array
        //Another way is Bottom-up, we can build the large array from the small array step by step
        aux = new Comparable[data.length];
        int mid = lo + (hi - lo)/2;
        if(lo >= hi) return ;
        mergeSortIml(data, lo, mid);
        mergeSortIml(data, mid + 1, hi);
        merge(data,lo,mid,hi);
    }


    public T[] quickSort(){
        quickSortIml(data, 0, data.length - 1);
        return data;
    }

    private void quickSortIml(T[] data,int lo, int hi){
//        if(lo >= hi) return;
        //This is a optimize method , the tiny sub array sorted by the insertion sort
        //7 is the size of sub array need to be treated diff, it can be substituted in the range[5,15]
        if(lo + 7 >= hi) {insertionSortIml(data,lo,hi);return;}
        int j = partition(data,lo,hi);
        //exclude the j , because the jth element is in the final place
        quickSortIml(data,lo,j-1);
        quickSortIml(data,j+1,hi);
    }

    private int partition(T[] data,int lo, int hi){
        //partition the data,get the index j which make the a[lo,j-1] <= a[j] <= a[j+1,hi]
        T key = data[lo];
        int i = lo;
        int j = hi + 1;
        while(true){
            //scan the left,stop at the one >= key
            while (less(data[++i],key))if(i >= hi) break;
            //scan the right,stop at the one <= key
            while (less(key,data[--j]))if(j <= lo) break;
            //i,j cross,complete this iteration
            if(i >= j) break;
            //exchange the out order pair
            exch(data,i,j);
        }
        //if the lo don't cross the hi, we need to exchange the lo and j to preserve invariant
        exch(data,lo,j);
        return j;
    }
//    public static void main(String[] args) {
//        Integer[] input = {2,1,53,42,1,2,1,1,1,1,1,1,1,2,2,2,2,2,3,3,3,3};
//        Sorter<Integer> sorter = new Sorter<>(input);
//        input = sorter.quickSort();
//        for(int i:input)
//            System.out.println(i);
//    }
}
