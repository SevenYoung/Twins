package sse.ustc.ds;


import java.util.NoSuchElementException;

/**
 * Created by SevenYoung on 15-6-5.
 */
public class BST <Key extends Comparable<Key>,Value>{
    //The Binary Search Tree can serve as Symbol Table
    private Node root;//Every node is the root of its subtree

    public class Node{
        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public int getN() {
            return N;
        }

        public void setKey(Key key) {
            this.key = key;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setN(int n) {
            N = n;
        }

        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int N;//the number of nodes in this subtree
        Node(Key key,Value value,int N){
            this.key = key;
            this.value = value;
            this.N = N;
        }

    }

    public BST(){root = null;}

    public void put(Key key,Value value){
        root = put(root,key,value);
    }

    private Node put(Node root,Key key,Value value){
        //put the K/V pair into this BST ,return the root of the new Tree
        //The BST is empty,the first node is the root of this tree
        if(root == null) return new Node(key,value,1);
        //The BST is not empty
        else {
            //The recursive function,you need to remember three things:
            //(1)Process the start condition and current condition
            //(2)Decrease the problem size
            //(3)Clarify define the return of the function,and use it
            int cmp = key.compareTo(root.key);
            if(cmp < 0)         root.left = put(root.left, key, value);
            else if(cmp > 0)    root.right =  put(root.right, key, value);
            else                root.value = value;
            root.N = 1 + size(root.left) + size(root.right);
            return root;
        }

    }

    public int size(Node root){
        if(root == null) return 0;
        else return root.N;
    }

    public int size(){return size(root);}

    private Node get(Node root,Key key){
        //Get the value associated this key,return the Node of this key
        if(root == null)return null;//this tree is empty,of course don't have this key
        else {
            int cmp = key.compareTo(root.key);
            if(cmp < 0)         return get(root.left,key);
            else if(cmp > 0)    return get(root.right,key);
            else return root;
        }
    }

    public Value get(Key key){
        Node item = get(root,key);
        if(item == null) return null;
        else return item.value;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public boolean contains(Key key){
        return get(key)!= null;
    }

    private int rank(Node root,Key key){
        //How many nodes in the subtree root smaller than this key
        if(isEmpty()) return 0;
        else {
            int cmp = key.compareTo(root.key);
            //This key is smaller than root's key,so the rank in root is equal to the rank in the root.left
            if(cmp < 0)         return rank(root.left,key);
            //This key is larger than root.key,so the rank in root is equal to the root.left  size + current root 1 + rank in the root.right
            else if(cmp > 0)    return size(root.left) + 1 + rank(root.right,key);
            //This key is the key we need,its rank is root.left size means the number of nodes smaller than this key
            else                return size(root.left);
        }
    }

    public int rank(Key key){
        //How many nodes in this tree smaller than this key
        return rank(root,key);
    }

    private Node min(Node root){
        //get the node containing the minimum key
        if(root == null) return null;
        if(root.left == null) return root;
        return min(root.left);
    }

    public Key min(){
        //get the minimum key
        Node min = min(root);
        if(min == null) return null;
        else return min.key;
    }

    private Node max(Node root){
        //get the node containing the maximum key
        if(root == null) return null;
        if(root.right == null) return root;
        else return max(root.right);
    }

    public Key max(){
        //get the maximum key
        Node max = max(root);
        if(max == null) return null;
        else return max.key;
    }

    public Key select(int k){
        return select(root,k);
    }

    private Key select(Node root,int k){
        if(root == null) return null;
        int rank = rank(root.key);
        if(k < rank)        return select(root.left, k);
        else if(k > rank)   return select(root.right, k - rank - 1);
        else return root.key;
    }

    private Node floor(Node root,Key key){
        //Find the node which key is the largest one smaller or equal to the key
        if(root == null) return null;
        else{
            int cmp = key.compareTo(root.key);
            //This key is smaller than root's key,then key's floor can only appear in root left subtree
            //In this situation,the floor of this key may be null
            if(cmp < 0) return floor(root.left,key);
            //This key is larger than root's key,then key's floor can only appear in root right subtree
            //In this situation,the floor of this key never be null,if we can't find key in the right subtree,then current root is floor
            else if(cmp > 0){
                Node t = floor(root.right,key);
                if(t != null) return t;
                else return root;
            }
            //This key is equal to the root's key,then root's key is floor of this key
            else return root;
        }
    }

    public Key floor(Key key){
        Node floor = floor(root,key);
        if(floor == null) return  null;
        else return floor.key;
    }

    private Node ceiling(Node root,Key key){
        //Find the node which key is the smallest on larger or equal to the key
        if(root == null) return null;
        else {
            int cmp = key.compareTo(root.key);
            //This key is smaller than root's key,the ceiling of this key only can appear in left subtree,if not,it is current root
            if(cmp < 0){
                Node t = ceiling(root.left,key);
                if(t != null) return t;
                else return root;
            }
            //This key is larger than root's key, then ceiling of this key only can appear in right subtree.
            else if(cmp > 0) return ceiling(root.right,key);
            else return root;
        }
    }

    public Key ceiling(Key key){
        Node ceiling = ceiling(root,key);
        if(ceiling == null) return  null;
        else return ceiling.key;
    }

    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node root){
        //Delete the min key,return the new tree
        if(root == null) throw new NoSuchElementException("The Tree is empty");
        else {
            //The current root is the min,return the successor(root.right)
            if(root.left == null) return root.right;
            //The current root is not the min,the min must be in the left subtree,we should deleteMin in left subtree
            root.left =  deleteMin(root.left);
            //Update the root's size
            root.N = size(root.left) + size(root.right) + 1;
            return root;
        }
    }

    public void deleteMax(){
        root = deleteMax(root);
    }

    private Node deleteMax(Node root){
        //Delete the max key,return the new tree
        if(root == null) throw new NoSuchElementException("The Tree is empty");
        else {
            if(root.right == null) return root.left;
            //The right subtree is not empty,we should modify the root's right point to deleted new tree
            root.right = deleteMax(root.right);
            root.N = size(root.left) + size(root.right) + 1;
            return root;
        }
    }

    private Node delete(Node root,Key key){
        //Delete the specified key and its value,return the new tree's root
        if(root == null) return null;
        else {
            int cmp = key.compareTo(root.key);
            //This key is less than the root's key,we should delete this key in the left subtree
            if(cmp < 0) root.left = delete(root.left,key);
            //This key is larger than the root's key,we should delete this key in the right subtree
            else if(cmp > 0) root.right = delete(root.right,key);
            //This key is equal to this root's key,fuck it now!
            else{
                //Left child is empty
                if(root.left == null) return root.right;
                //Right child is empty
                if(root.right == null) return root.left;
                //The left and right all exists
                Node t = root;                  //Record current root(need to be deleted)
                root = min(t.right);            //Change this root to the successor(the min of root right subtree)
                root.right = deleteMin(t.right);//The new root's right is precious root's right subtree without min key/value
                root.left = t.left;             //The new root's left is equal to precious root's left subtree.
            }
            //Update the size of tree(will recurse back)
            root.N = size(root.left) + size(root.right) + 1;
            return root;
        }
    }

    public void delete(Key key){
        root = delete(root,key);
    }

    public int size(Key lo,Key hi){
        if(lo.compareTo(hi) > 0) return 0;
        if(contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    public Iterable<Key> keys(Key lo,Key hi){
        LinkedQueue<Key> queue = new LinkedQueue<>();
        keys(root,queue,lo,hi);
        return queue;
    }

    public Iterable<Key> keys(){
        return keys(min(),max());
    }

    private void keys(Node root,LinkedQueue<Key> queue,Key lo,Key hi){
        if(root == null) return ;
        else {
            int loCmp = lo.compareTo(root.key);
            int hiCmp = hi.compareTo(root.key);
            //If the range cover part of left subtree,then we should collect the key in left subtree too
            if(loCmp < 0)keys(root.left,queue,lo,hi);
            //If the root's key is in this range,of course it should be enqueued
            if(loCmp <= 0 && hiCmp >= 0) queue.enqueue(root.key);
            if(hiCmp > 0) keys(root.right,queue,lo,hi);
        }
    }

    public void display(){
        display(root);
    }

    void display(Node root){
        System.out.println("<" + root.getKey() + "," + root.getValue() + ">");
        if(root.getLeft() != null)
            display(root.getLeft());
        if(root.getRight() != null)
            display(root.getRight());
    }

//    public static void main(String[] args) {
//        String[] example = {"s", "e", "a", "r", "c", "h", "e", "x", "a", "m", "p", "l", "e"};
//        BST<String, Integer> bst = new BST<>();
//        int value = 0;
//        for (String i : example) {
//            bst.put(i, value++);
//        }
//        bst.display();
//        System.out.println();

//        System.out.println(bst.ceiling("f"));

//        for(String i: bst.keys("a","x"))
//            System.out.println(i);
//        System.out.println(bst.size("a", "h"));
//        System.out.println(bst.rank("m"));


//        LinkedQueue<String > lq= (LinkedQueue<String>)bst.keys();
//        while(!lq.isEmpty())
//            System.out.println(lq.dequeue());
////





//        bst.delete("l");
//        bst.display();
//        String min = bst.min();
//        System.out.println(bst.get(min));
//        String max = bst.max();
//        System.out.println(bst.get(max));

//        bst.deleteMax();
//        bst.display();
//        System.out.println();
//        bst.deleteMin();
//        bst.display();

//    }


}
