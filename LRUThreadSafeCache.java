package util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LRUThreadSafeCache<K,V> {

    private class Node {
        K key;
        V value;
        Node prev, next;

        Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private Map<K,Node> map;
    private ReentrantLock lock = new ReentrantLock();
    private Node head, tail;

    public LRUThreadSafeCache(int capacity){
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
        
    }
    public V get(K key){
        lock.lock();
        try{
            Node node = map.get(key);
            if(node == null) return null;
            moveToHead(node);
            return node.value;
        } finally{
            lock.unlock();
        }
    }

    public void put(K key, V value){
        lock.lock();
        try{
            Node node = map.get(key);
            if(node != null){
                node.value =  value;
                moveToHead(node);
            } else {
                Node newNode = new Node(key, value);
                map.put(key, newNode);
                addToHead(newNode);
            }
            if(map.size() > capacity){
                Node lru = removeTail();
                map.remove(lru.key);
            }
        } finally{
            lock.unlock();
        }
    }

    private void addToHead(Node node){
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private Node removeTail() {
        Node lru = tail.prev;
        removeNode(lru);
        return lru;
    }


}
