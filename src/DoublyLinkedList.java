import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Luis Palomino
 * @since 10/13/2017
 * @param <V> Generic value
 */
public class DoublyLinkedList<V> implements Iterable<V>{
    
    private int size;
    private Node<V> head;
    private Node<V> tail;
    
    /**
     * Class constructor
     */
    public DoublyLinkedList(){
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    /**
     * 
     * @return returns the size of the list
     */
    public int size(){
        return this.size;
    }
    
    /**
     * 
     * @return returns if the list is empty
     */
    public boolean isEmpty(){
        return this.size==0;
    }
    
    /**
     * Adds the value at the first position
     * @param value to add
     */
    public void addFirst(V value){
        Node<V> tmp = new Node<V>(value);
        tmp.next = this.head;
        if(this.isEmpty()){
            this.tail = tmp;
        }else{
            this.head.prev = tmp;
        }
        this.head = tmp;
        this.size++;
    }
    
    /**
     * Adds the value at the last position
     * @param value
     */
    public void addLast(V value){
        if(this.isEmpty()){
            this.addFirst(value);
        }else{
            Node<V> tmp = new Node<V>(value);
            tmp.prev = this.tail;
            this.tail.next = tmp;
            this.tail = tmp;
            this.size++;
        }
        
    }
    
    /**
     * Adds the value at the specified position
     * @param value
     * @param index
     */
    public void addAt(V value, int index){
        if(index < 0 || index > this.size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            this.addFirst(value);
        }
        else if(index == this.size){
            this.addLast(value);
        }else{
            Node<V> tmp = new Node<V>(value);
            Node<V> current = this.head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
            tmp.next = current;
            tmp.prev = current.prev;
            current.prev.next = tmp;
            current.prev = tmp;
            this.size++;
        }
    }
    
    /**
     * 
     * @param value
     * @return returns the index of the value
     */
    public int indexOf(V value){
        Node<V> current = this.head;
        int index = 0;
        while(current != null){
            if(current.value.equals(value)){
                return index;
            }
            index++;
            current = current.next;
        }
        throw new NoSuchElementException();
    }
    
    /**
     * Removes the index from the list
     * @param index
     * @return returns the value asignated to the index
     */
    public V remove(int index){
        if(index < 0 || index > this.size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            return this.removeFirst();
        }
        if(index == this.size-1){
            return this.removeLast();
        }
        Node<V> current = this.head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        V value = current.value;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current = null;
        this.size--;
        return value;
    }
    
    
    /**
     * Removes the first position
     * @return
     */
    public V removeFirst(){
        if(this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        V value = this.head.value;
        this.head = this.head.next;
        this.size--;
        return value;
    }
    
    /**
     * Removes the last position
     * @return
     */
    public V removeLast(){
        if(this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        V value = this.tail.value;
        this.tail = this.tail.prev;
        this.tail.next = null;
        this.size--;
        return value;
        
    }
    
    /**
     * Returns the position at a certain index
     * @param index
     * @return
     */
    public V get(int index){
        if(index < 0 || index > this.size){
            throw new IndexOutOfBoundsException();
        }
        Node<V> current = this.head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        return current.value;
    }
    
    /**
     * Returns the first position
     * @return
     */
    public V getFirst(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
        return this.head.value;
        
    }
    
    /**
     * 
     * @param value
     * @return returns true if the value is on the list
     */
    public boolean contains(V value){
        Node<V> current = this.head;
        while(current != null){
            if(current.value.equals(value)) return true;
            current = current.next;
        }
        return false;
    }

    /**
     * Returns the last position
     * @return
     */
    public V getLast(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
        return this.tail.value;
    }
    
    /**
     * Returns the linked list in String format
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<V> current = this.head;
        while(current!= null){
            sb.append(current.value + ", ");
            current = current.next;
        }
        sb.append("]");
        
        return sb.toString();
    }
    
    static class Node<V>{
        
        V value;
        Node<V> next, prev;
        
        public Node(V value){
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new DoubleListIterator<>(this.head);
    }

    /**
     * Define the iterator from the class
     * @author Luis Palomino
     *
     * @param <T>
     */
    private static class DoubleListIterator<T> implements Iterator<T> {

        private Node<T> current, lastReturn;
        
        public DoubleListIterator(Node<T> nodo){
            current=nodo;
            lastReturn=null;
        }
        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            lastReturn=current;
            current=current.next;
            return lastReturn.value;
        }

    }

}
