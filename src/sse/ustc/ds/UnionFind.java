package sse.ustc.ds;

import sse.ustc.utils.StdIn;
import sse.ustc.utils.StdOut;

/**
 * Created by SevenYoung on 15-5-29.
 */
//The UnionFind structure is useful in the problems relating with connectivity.
//In this class i use the weighted-quick-union implementation which seems to be the most opt algorithm
public class UnionFind {
    //The improvement steps of this algorithm are:
    //(1)Quick-Find ---> Quick-Union(the flatten array to tree, indicates O(n) -> O(lgn) )
    //(2)Quick-Union ---> Weighted-Union(tree to balance tree, guarantee the worst case)
    //(3)Weighted-Union ---> path compress(short the search distance)

    //Fields
    private int[] pId;//The content in pId[i] is ith object's parent id.
    private int[] size;//The content in size[i](i always root id) is the component i's size
    private int count;//The number of components

    //Constructor
    UnionFind(int N){
        pId = new int[N];
        size = new int[N];
        for(int i=0; i<N; i++){
            pId[i] = i;
            size[i] = 1;
        }
        count = N;
    }

    //Methods
    //Find out the root of the object p
    public int find(int p){
        int N = pId.length;
        if(p < 0 || p >= N) throw new IndexOutOfBoundsException("index" + p + "is not between 0 and " + N);
        while(p != pId[p]) {
            pId[p] = pId[pId[p]];//path compression by halving(now and later),this array is used for searching parent(root)!
            p = pId[p];
        }
        return p;
    }

    //The number of the components
    public int count(){return count;}

    //Whether the object p connects to object q
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }

    public void union(int p, int q){
        if(isConnected(p,q)) return;
        int pRoot = find(p);
        int qRoot= find(q);
        if(size[pRoot] < size[qRoot]) {pId[pRoot] = qRoot; size[qRoot] += size[pRoot];}
        else{pId[qRoot] = pRoot; size[pRoot] += size[qRoot]; }
        count --;
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        UnionFind uf = new UnionFind(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.isConnected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

}
