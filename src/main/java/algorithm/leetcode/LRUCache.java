package algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author junlin_huang
 * @create 2021-04-12 下午10:26
 **/

class LRUCache {

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(3);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        lRUCache.put(3,3); // 缓存是 {1=1}
        lRUCache.put(4, 4); // 缓存是 {1=1, 2=2}
        lRUCache.get(4);    // 返回 1
        lRUCache.get(3);
        lRUCache.get(2);
        lRUCache.get(1);
        lRUCache.put(5,5);
        lRUCache.get(1);
        lRUCache.get(2);
        lRUCache.get(3);
        lRUCache.get(4);
        lRUCache.get(5);

    }

    private Map<Integer,Node> map = new HashMap();
    private int size;
    private int capacity;
    private Node head;
    private Node tail;

    class Node {
        private Node pre;
        private Node next;
        private int key;
        private int val;
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        remove(node);
        addToTail(node);
        return node.val;
    }

    public void put(int key, int value) {
        if(!map.containsKey(key)) {
            Node node = new Node();
            node.key = key;
            node.val = value;
            addToTail(node);
            map.put(key,node);
            size++;
            if(size > capacity) {
                removeOldest();
            }
        } else {
            Node node = map.get(key);
            node.val = value;
            remove(node);
            addToTail(node);
        }
    }

    public void remove(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    public void addToTail(Node node){
        Node tailPre = tail.pre;
        tailPre.next = node;
        node.pre = tailPre;
        node.next = tail;
        tail.pre = node;
    }

    public void removeOldest() {
        Node node = head.next;
        head.next = node.next;
        node.next.pre = head;
        size--;
        map.remove(node.key);
    }
}