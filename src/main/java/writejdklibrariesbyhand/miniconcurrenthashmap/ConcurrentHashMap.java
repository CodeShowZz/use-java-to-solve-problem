package writejdklibrariesbyhand.miniconcurrenthashmap;

import sun.misc.Unsafe;
import writejdklibrariesbyhand.myreentrantlock.AQS;

import java.lang.reflect.Field;

/**
 * @author junlin_huang
 * @create 2021-02-05 上午2:36
 **/
public class ConcurrentHashMap<K, V> {

    transient volatile Node<K, V>[] table;

    private transient volatile int sizeCtl;

    private static final int DEFAULT_CAPACITY = 16;

    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    private static final sun.misc.Unsafe U;

    private static final long SIZECTL;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            U = (Unsafe) unsafeField.get(null);
            SIZECTL = U.objectFieldOffset
                    (ConcurrentHashMap.class.getDeclaredField("sizeCtl"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K, V> next;

        Node(int hash, K key, V val, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public ConcurrentHashMap() {

    }

    public V put(K key, V value) {
        return putVal(key, value, false);
    }

    final V putVal(K key, V value, boolean onlyIfAbsent) {
        if (key == null || value == null) throw new NullPointerException();
        int hash = spread(key.hashCode());
        int binCount = 0;
        for (Node<K, V>[] tab = table; ; ) {
            Node<K, V> f;
            int n, i, fh;
            if (tab == null || (n = tab.length) == 0)
                tab = initTable();
            return null;
        }
    }

    /**
     * 该方法目的是使得hash值的分布更加均匀
     *
     * @param h
     * @return
     */
    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    private final Node<K, V>[] initTable() {
        Node<K, V>[] tab;
        int sc;
        while ((tab = table) == null || tab.length == 0) {
            if ((sc = sizeCtl) < 0)
                Thread.yield(); //sizeCtl小于0说明此时在初始化表,尽量不抢占CPU
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {  //只有一个线程能成功执行
                try {
                    if ((tab = table) == null || tab.length == 0) { //再次判断 防止表创建完成之后再次重复创建
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        @SuppressWarnings("unchecked")
                        Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[n];
                        table = tab = nt;
                        sc = n - (n >>> 2); //相当于乘以0.75
                    }
                } finally {
                    sizeCtl = sc;
                }
                break;
            }
        }
        return tab;
    }

    public static void main(String[] args) {
        java.util.concurrent.ConcurrentHashMap concurrentHashMap = new java.util.concurrent.ConcurrentHashMap();
        concurrentHashMap.put("a", "1");
    }
}