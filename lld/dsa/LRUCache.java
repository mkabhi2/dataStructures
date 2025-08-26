package lld.dsa;
import java.util.*;

//LRU Cache means the least recently used cache. That means if the cache gets full then the least recently used entry
//should be deleted. All cache operations, get. put, delete should be done in O(1) time complexity.
// We will use a HashMap for fast access and a DoublyLinkedList to maintain the LRU structure.
// Each value in the HashMap is a Node of the LinkedList

class Node<K, V> {      //Generic to keep any type of key, value pair
    K key;              //Key is also part of node so that given a node to delete from the list, we know the key,
                        // value pair to be removed from the app
    V value;
    Node<K,V> next;
    Node<K,V> prev;
    public Node(K key, V value){ this.key = key; this.value=value;}
}

// The doublyLinkedList will be a custom implementation. We will maintain the Head and tail ptrs of the DLL.
// Head will point to most recently used and tail will point to least recently used node

class DoublyLinkedList<K,V>{
    Node<K,V> head;                 //Head and tail ptrs will always point to dummy nodes, actual head will be second node
    Node<K,V> tail;                 // and tail will be second last node
    public DoublyLinkedList(){
        head = new Node<K,V>(null,null);
        tail = new Node<K,V>(null,null);
    }

    //Functions to add delete nodes from the list, always keeping the dummy nodes intact
}

public class LRUCache<K,V> {
    Map<K, Node<K,V>> map;      //Map of Key and Values as Node
    DoublyLinkedList<K,V> dll;
    int capacity;

    public LRUCache(int capacity){
        this.capacity = capacity;
        map = new HashMap<>();          //We use hashMap instead of concurrentHashMap, because each get or put means
        dll = new DoublyLinkedList<>(); //updating both the map and dll, and their no concurrent equivalent of dll
                                        //so we will have to anyway use synchronized keyword
    }

    // get by key involves, fetching the Node from map by the key, if it exists
    // Then pulling out the returned node from dll and then add it to the front of the list
    public synchronized V get(K key){return null;}

    // put by key involves, updating[just the V value] or adding the key, value pair to the map
    // Then pulling out the returned node from dll and then add it to the front of the list, if new directly add to front
    public synchronized void put(K key, V value){}
}
