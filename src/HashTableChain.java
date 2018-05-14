import java.math.BigInteger;
import java.util.Random;

public class HashTableChain<K, V>{

    private int size;
    private Node<K, V>[] table;
    private int occupied;
    private static final double OVERLOAD_FACTOR = 7.0/8;
    private static final int INIT_CAPACITY = 1;
    
    public HashTableChain(){
        this(INIT_CAPACITY);
        
    }

    public HashTableChain(int size){
        this.size = size;
        this.table = (Node<K, V>[]) new Node[this.size];
        this.occupied = 0;
    }
    
    private int HashDiv(K key){
        int s = key.hashCode();
        return Math.abs(s)%this.size;
    }

    public int size(){
        return this.occupied;
    }

    public boolean isEmpty(){
        return this.occupied == 0;
    }

    public V get(K key){
        if(key == null){
            throw new NullPointerException("Null key");
        }
        int pos = this.HashDiv(key);
        Node<K, V> tmp = this.table[pos];
        while(tmp != null){
            if(tmp.key.equals(key)) return tmp.value;
            tmp = tmp.next;
        }
        return null;
    }
    
    public void put(K key, V value){
        if(key == null || value == null){
            throw new NullPointerException("Null value or key");
        }
        if((double)this.occupied/this.size > HashTableChain.OVERLOAD_FACTOR){
            this.reHash();
        }
        int pos = this.HashDiv(key);
        Node<K, V> tmp = this.table[pos]; 
        
        while(tmp != null){
            if(tmp.key.equals(key)){
                tmp.value = value;
                return;
            }
            tmp = tmp.next;
        }
        Node<K, V> newNode = new Node<>(key, value);
        Node<K, V> firstNode = this.table[pos];
        newNode.next = firstNode;
        this.table[pos] = newNode;
        this.occupied++;
    }
    
    public V remove(K key){
        if(key == null){
            throw new NullPointerException("Null key");
        }
        int pos = this.HashDiv(key);
        Node<K, V> current = this.table[pos];
        Node<K, V> prev = null;
        while(current != null){
            if(current.key.equals(key)){
                V val = current.value;
                if(prev == null){
                    this.table[pos] = current.next;
                }else{
                    prev.next = current.next;
                }
                current = null;
                return val;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }
    
    private void reHash(){
        this.size*=2;
        Node<K, V>[] tableCopy = this.table;
        this.table = (Node<K, V>[]) new Node[this.size];
        this.occupied = 0;
        for(int i = 0; i < tableCopy.length; i++){
            Node<K, V> current = tableCopy[i];
            while(current != null){
                this.put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public void printOccupied(){
        for(int i = 0; i < this.table.length; i++){
            int count = 0;
            Node<K, V> tmp = this.table[i];
            while(tmp != null){
                count++;
                tmp = tmp.next;
            }
            System.out.println(i + " --> " + count);
        }
    }
    
    class Node<K, V>{
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

}
