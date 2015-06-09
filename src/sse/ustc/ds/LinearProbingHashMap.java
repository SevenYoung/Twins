package sse.ustc.ds;

import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 * Created by SevenYoung on 15-6-8.
 */
public class LinearProbingHashMap <Key extends Comparable<Key>, Value>{
    private int N;//the number of k/v pairs in this hash map
    private int capacity;//the capacity of this hash map
    Key[] keys;
    Value[] values;

    LinearProbingHashMap(int capacity){
        this.capacity = capacity;
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Comparable[capacity];
    }

    public int size(){
        return N;
    }

    private void resize(int newCapacity){
        LinearProbingHashMap<Key,Value> temp = new LinearProbingHashMap<>(newCapacity);
        for(int i=0; i<capacity; i++)
            if(keys[i] != null)
                temp.put(keys[i],values[i]);
        keys = temp.keys;
        values = temp.values;
        capacity = newCapacity;//Please remember maintain every states of this object!
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    public void put(Key key,Value value){
        if(N > capacity/2) resize(2*capacity);
        int hashValue = hash(key);
        if(keys[hashValue] == null) {keys[hashValue] = key;values[hashValue] = value; N++; return;}
        int i = hashValue;
        for(; keys[i]!=null ; i=(i+1)%capacity){
            if(keys[i].compareTo(key) == 0) {values[i] = value; return;}
        }
        keys[i] = key;
        values[i] = value;
        N++;
    }

    public Value get(Key key){
        int hashValue = hash(key);
        if(keys[hashValue] == null) return null;
        int i = hashValue;
        for(;keys[i]!=null; i=(i+1)%capacity){
            if(keys[i].compareTo(key) == 0) return values[i];
        }
        return null;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public boolean contains(Key key){
        return get(key) != null;
    }

    public void delete(Key key){
        if(!contains(key)) return;

        int i;
        for(i=hash(key); keys[i]!=null; i=(i+1)%capacity){
            if(keys[i].compareTo(key) == 0) {
                keys[i] = null;
                values[i] = null;
                i = (i+1)%capacity;
                break;
            }
        }

        for(; keys[i]!=null; i=(i+1)%capacity){
            Key tmpKey = keys[i];
            Value tmpValue = values[i];
            keys[i] = null;
            values[i] = null;
            N--;
            put(tmpKey,tmpValue);

        }

        N--;

        if(N > 0 && N < capacity/8) resize(capacity/2);
    }

    Iterable<Key> keys(){
        LinkedQueue<Key> q = new LinkedQueue<>();
        for(Key i:keys)
            if(i != null)
                q.enqueue(i);
        return q;
    }

    public void display(){
        for(int i=0; i<capacity; i++){
            if(keys[i] != null){
                System.out.println("<" + keys[i] + "," + values[i] + ">" );
            }
        }
    }

//    public static void main(String[] args) {
//        String[] example = {"s", "e", "a", "r", "c", "h", "e", "x", "a", "m", "p", "l", "e"};
//        LinearProbingHashMap<String,Integer> hashMap = new LinearProbingHashMap<>(16);
//        int value = 0;
//        for(String key:example)
//            hashMap.put(key,value++);
//
//        hashMap.display();
//        System.out.printf(" " + hashMap.size());
//
//
//    }


}

