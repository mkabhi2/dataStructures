package lld.dsa;

// LFUCache means least frequently used cache, which means that if the cache becomes full then the object with the lowest
// access frequency should be deleted to make room for new inserts in the cache. The frequency for an object increases
// by 1, every time a get or put operation is performed on it. GET, PUT and DELETE operations should be done in O(1) time.
// For LFU, we keep, a map that maps keys to their corresponding node which contains the value and frequency of access.
// A freq_map that maps frequencies(integers) to a doubly linked list containing all nodes with that frequency.
// A variable min_freq to keep track of the minimum frequency of all keys in the cache for easy access to the least
// frequently used item.

import java.util.HashMap;
import java.util.Map;

//A node for the dll
class LFUNode<K,V>{
    K key;
    V value;
    int frequency;      //Tracks the total number of times this value has been accessed
    LFUNode<K,V> prev;
    LFUNode<K,V> next;

    public LFUNode(K key, V value){ this.key = key; this.value=value;}
}

class LFUDoublyLinkedList<K,V>{
    LFUNode<K,V> head;
    LFUNode<K,V> tail;

    public LFUDoublyLinkedList(){
        head = new LFUNode<K,V>(null,null);
        tail = new LFUNode<K,V>(null,null);
    }

    //Functions to add delete nodes from the list, always keeping the dummy nodes intact
}

public class LFUCache<K,V> {

    Map<K,Node<K, V>> cache;    //The map to store key-node pairs
    Map<Integer, DoublyLinkedList<K,V>> frequencyMap; //Map to store frequency to node list pairs
    int capacity;           //Capacity of the cache
    int minimumFrequency;   //Minimum Frequency of a node in the cache

    public LFUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        frequencyMap = new HashMap<>();
    }

    // Get method
    // Check if the key is present in cache map, if yes proceed ahead otherwise return null
    // Retrieve the dll node corresponding to the key from the cache map
    // Remove the node from wherever its prev and next are pointing to, this is O(1) operation
    // if the removal of the node made the prev freq DLL empty, then check if minimumFrequency of the LFUCache needs to be updated
    // Increment the frequency inside the node, and add the node to the corresponding frequency dll in the frequency map.
    // Either on the head or tail


    //Put Method
    // Check if key is already present in cache map, if already present then retrieve the dll node from the cache map,
    // remove the dll node from wherever its next and prev were pointing to
    // If the removal of the dll node made the prev freq DLL empty, then check if minimumFrequency of the LFUCache needs to be updated.
    // Change the value of the node to the new value and increment its frequency
    // Add the node to the head of the DLL of the new frequency in the frequencyMap.
    // If the key is not present, and the cache is full then remove the head/tail node of the DLL of the minimumFrequency.
    // If the key is present, and you have emptied space if full, then create a new node and key and add it to the cache map
    // Now add the node to the frequencyMap under key 1.

    //In both add and put check if the key is present in the frequencyMap while adding the node against the frequency,
    //if the key is not present, add the node and init the dll against it, adding the node.


}
