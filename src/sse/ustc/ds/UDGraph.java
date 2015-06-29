package sse.ustc.ds;


import sse.ustc.utils.In;
import sse.ustc.utils.StdOut;

import java.util.Iterator;

/**
 * Created by SevenYoung on 15-6-25.
 */
public class UDGraph{
    private int V_num;
    private int E_num;
    private LinkedList<Integer>[] vertexes;
    public UDGraph(int v){
        V_num = v;
        vertexes = new LinkedList[V_num];
        for(int i=0; i<V_num; i++)
            vertexes[i] = new LinkedList<>();
        E_num = 0;
    }

    public UDGraph(In in){
        this(in.readInt());
        int eNum = in.readInt();
        for(int i=0; i<eNum; i++){
            int v1 = in.readInt();
            int v2 = in.readInt();
            addEdge(v1, v2);
        }
        in.close();
    }

    public int V(){return V_num;}

    public int E(){return E_num;}

    public void addEdge(int v,int w){
        vertexes[v].insertLast(w);
        vertexes[w].insertLast(v);
        E_num++;
    }

    public Iterable<Integer> adj(int v){return vertexes[v];}

    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(V_num + " Vertexes" + "," + E_num + " Edges" + "\n");
        for(int v=0; v < V(); v++)
        {
            StringBuilder tmp = new StringBuilder();
            tmp.append(v + ":");
            for(int i:vertexes[v])
                tmp.append(i + " ");
            tmp.append("\n");
            res.append(tmp);
        }
        return res.toString();
    }

    public static class DepthFirstSearch{
        private UDGraph target;
        private int source;
        private boolean[] marked;
        private int count;
        public DepthFirstSearch(UDGraph G,int s){
            target = G;
            source = s;
            count = 0;
            marked = new boolean[G.V()];
            dfs(target,source,marked);
        }

        private void dfs(UDGraph G,int s,boolean[] marked){
            marked[s] = true;
            count++;
            for(int i:G.adj(s))
                if(!marked[i]) dfs(G,i,marked);
        }

        public int connectivy(){
            boolean[] tmp = new boolean[target.V()];

            int count = 0;
            for(int i=0 ; i<target.V(); i++)
                if(!tmp[i]) {
                    count++;
                    dfs(target,i,tmp);
                }
            return count;
        }


        public boolean marked(int v){
            //Is v connected to s?
            return marked[v];
        }

        public int count(){
            return count;
        }
    }

    public static class DepthFirstPaths{
        private UDGraph target;
        private int source;
        private boolean[] marked;
        private int[] edgeTo;

        public DepthFirstPaths(UDGraph target,int source){
            this.target = target;
            this.source = source;
            marked = new boolean[target.V()];
            edgeTo = new int[target.V()];
            dfs(target,source);
        }

        private void dfs(UDGraph target,int source){
            marked[source] = true;
            for(int s:target.adj(source))
                if(!marked[s]){
                    edgeTo[s] = source;
                    dfs(target,s);
                }
        }


        public boolean hasPathTo(int v){
            return marked[v];
        }

        Iterable<Integer> pathTo(int v){
            //For finding the path,we can get the edge to current vertex,then trace back the edge to the source
            LinkedStack<Integer> ls = new LinkedStack<>();
            for(int i=v; i!=source; i = edgeTo[i])
                ls.push(i);
            ls.push(source);
            return ls;
        }
    }

    public static class BreadFirstPaths{
        private UDGraph target;
        private int source;
        private boolean[] marked;
        private int[] edgeTo;
        private LinkedQueue<Integer> lq;
        public BreadFirstPaths(UDGraph target,int source){
            this.target = target;
            this.source = source;
            marked = new boolean[target.V()];
            edgeTo = new int[target.V()];
            lq = new LinkedQueue<>();
            marked[source] = true;
            lq.enqueue(source);//This the start point
            bfs(target);
        }
        private void bfs(UDGraph target){
            Integer s = lq.dequeue();
            if(s == null) return;
            for(int i: target.adj(s))
                if(!marked[i])
                {
                    marked[i] = true;
                    edgeTo[i] = s;
                    lq.enqueue(i);
                }
            bfs(target);
        }

        public boolean hasPathTo(int v){
            return marked[v];
        }

        Iterable<Integer> pathTo(int v) {
            //For finding the path,we can get the edge to current vertex,then trace back the edge to the source
            LinkedStack<Integer> ls = new LinkedStack<>();
            for (int i = v; i != source; i = edgeTo[i])
                ls.push(i);
            ls.push(source);
            return ls;
        }
    }

    public static class CC{
        private int[] marked;
        private int count;
        public CC(UDGraph target){
            marked = new int[target.V()];
            count = 0;
            for(int s=0; s<target.V(); s++){
                if(marked[s] < 1){
                    //The smallest id of component is 1 so that less than 1 means never be marked.
                    count++;
                    DFS(target,s,count);
                }
            }
        }

        private void DFS(UDGraph target,int s,int count){
            marked[s] = count;
            for(int n:target.adj(s))
                if(marked[n] != count)
                    DFS(target,n,count);
        }

        public int count(){return count;}
        public int id(int v){return marked[v]-1;}
        public boolean connected(int v, int w){return id(v) == id(w);}
    }



//    public static void main(String[] args) {
//        In in = new In("/Users/SevenYoung/Downloads/input");
//        UDGraph G = new UDGraph(in);
//        CC cc = new CC(G);
//        int M = cc.count();
//        StdOut.println(M + " components");
//        LinkedList<Integer>[] components;
//        components = (LinkedList<Integer>[]) new LinkedList[M];
//        for (int i = 0; i < M; i++)
//            components[i] = new LinkedList<Integer>();
//        for (int v = 0; v < G.V(); v++)
//            components[cc.id(v)].insertLast(v);
//        for (int i = 0; i < M; i++)
//        {
//            for (int v: components[i])
//                StdOut.print(v + " ");
//            StdOut.println();
//        }
////        DepthFirstSearch dfs = new DepthFirstSearch(udg,9);
//        for(int v=0; v<udg.V(); v++)
//            if(dfs.marked(v))
//                System.out.print(v + " ");
//        System.out.println();

//        System.out.println(udg);
//
//        System.out.println("depth first");
//        //Find the path from source to every other vertexes.
//        DepthFirstPaths dp = new DepthFirstPaths(udg,0);
//        for (int v = 0; v < udg.V(); v++)
//        {
//            StdOut.print(0 + " to " + v + ": ");
//            if (dp.hasPathTo(v))
//                for (int x : dp.pathTo(v))
//                    if (x == 0) StdOut.print(x);
//                    else StdOut.print("-" + x);
//            StdOut.println();
//        }
//
//        System.out.println("split!");
//
//        System.out.println("bread first");
//        BreadFirstPaths bf = new BreadFirstPaths(udg,0);
//        for (int v = 0; v < udg.V(); v++)
//        {
//            StdOut.print(0 + " to " + v + ": ");
//            if (bf.hasPathTo(v))
//                for (int x : bf.pathTo(v))
//                    if (x == 0) StdOut.print(x);
//                    else StdOut.print("-" + x);
//            StdOut.println();
//        }

//        System.out.println(dfs.count());
//        System.out.println(dfs.connectivy());
//        UDGraph udg = new UDGraph(5);
//        udg.addEdge(0,2);
//        udg.addEdge(1,2);
//        udg.addEdge(2,3);
//        udg.addEdge(2,4);
//        udg.addEdge(3,4);
//        System.out.println(udg);
//    }


}
