package sse.ustc.ds;

/**
 * Created by SevenYoung on 15-6-8.
 */
public class RBTree <Key extends Comparable<Key>, Value> extends BST<Key,Value>{
    //The setter and getter is
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private RBNode root;
    public class RBNode extends BST.Node{
        private Key key;
        private Value value;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private int N;//the number of nodes in this subtree
        RBNode(Key key,Value value,int N,boolean color){
            super(key,value,N);
            this.color = color;
        }

        @Override
        public Key getKey() {
            return (Key)super.getKey();
        }


        @Override
        public Value getValue() {
            return (Value)super.getValue();
        }

        @Override
        public RBNode getLeft() {
            return (RBNode)super.getLeft();
        }

        public void setLeft(RBNode left) {
            super.setLeft(left);
        }

        @Override
        public RBNode getRight() {
            return (RBNode)super.getRight();
        }

        public void setRight(RBNode right) {
            super.setRight(right);
        }


        public void setColor(boolean color) {
            this.color = color;
        }

        @Override
        public int getN() {
            return super.getN();
        }

        @Override
        public void setN(int n) {
            super.setN(n);
        }
    }

    public RBTree(){root = null;}

    public void put(Key key,Value value){
        root = put(root,key,value);
        root.color = BLACK;
    }

    private RBNode put(RBNode root,Key key,Value value){
        //put the K/V pair into this RBTree ,return the root of the new Tree
        //The RBTree is empty,the first node is the root of this tree
        if(root == null) return new RBNode(key,value,1,RED);
            //The BST is not empty
        else {
            //The recursive function,you need to remember three things:
            //(1)Process the start condition and current condition
            //(2)Decrease the problem size
            //(3)Clarify define the return of the function,and use it
            int cmp = key.compareTo(root.getKey());
            if(cmp < 0)         root.setLeft(put(root.getLeft(), key, value));
            else if(cmp > 0)    root.setRight(put(root.getRight(), key, value));
            else                root.setValue(value);

            //fix-up any right leaning red links
            if(!isRed(root.getLeft()) && isRed(root.getRight())) root = rotateLeft(root);
            if(isRed(root.getLeft()) && isRed(root.getLeft().getLeft())) root = rotateRight(root);
            if(isRed(root.getLeft()) && isRed(root.getRight()))   flipColor(root);

            root.setN(1 + size(root.getLeft()) + size(root.getRight()));
            return root;
        }

    }

    public void display(){
        display(root);
    }

    private boolean isRed(RBNode x){
        if(x == null) return false;
        return x.color == RED;
    }

    private void flipColor(RBNode root){
        root.color = !root.color;
        root.getLeft().color = !root.getLeft().color;
        root.getRight().color = !root.getRight().color;
    }

    private RBNode rotateLeft(RBNode root){
        RBNode x = root.getRight();
        root.setRight(x.getLeft());
        x.setLeft(root);
        x.color = root.color;
        root.color = RED;
        x.setN(root.getN());
        root.setN(size(root.getLeft()) + size(root.getRight()) + 1);
        root = x;
        return root;
    }

    private RBNode rotateRight(RBNode root){
        RBNode x = root.getLeft();
        root.setLeft(x.getRight());
        x.setRight(root);
        x.color = root.color;
        root.color = RED;
        x.setN(root.getN());
        root.setN(size(root.getLeft()) + size(root.getRight()) + 1);
        root = x;
        return root;
    }



    public static void main(String[] args) {
        String[] example = {"s", "e", "a", "r", "c", "h", "e", "x", "a", "m", "p", "l", "e"};
        RBTree<String, Integer> bst = new RBTree<>();
        BST<String, Integer> bst1 = new BST<>();
        int value = 0;
        for (String i : example) {
            bst1.put(i,value);
            bst.put(i, value++);
        }
        System.out.println("The normal BST:");
        bst1.display();
        System.out.println("The red black Tree:");
        bst.display();

//        System.out.println(bst.ceiling("f"));
//
//        for(String i: bst.keys("a","x"))
//            System.out.println(i);
//        System.out.println(bst.size("a", "h"));
//        System.out.println(bst.rank("m"));
//
//
//        LinkedQueue<String > lq= (LinkedQueue<String>)bst.keys();
//        while(!lq.isEmpty())
//            System.out.println(lq.dequeue());
////
//
//
//
//
//
//        bst.delete("l");
//        bst.display();
//        String min = bst.min();
//        System.out.println(bst.get(min));
//        String max = bst.max();
//        System.out.println(bst.get(max));
//
//        bst.deleteMax();
//        bst.display();
//        System.out.println();
//        bst.deleteMin();
//        bst.display();

    }

}
