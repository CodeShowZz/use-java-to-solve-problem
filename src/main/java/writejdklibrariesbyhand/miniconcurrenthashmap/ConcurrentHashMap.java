package writejdklibrariesbyhand.miniconcurrenthashmap;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author junlin_huang
 * @create 2021-02-05 上午2:36
 *
 *
 *
 *
 **/
public class ConcurrentHashMap<K, V> {

    transient volatile Node<K, V>[] table;

    private transient volatile int sizeCtl;

    private static final int DEFAULT_CAPACITY = 16;

    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    private static final sun.misc.Unsafe U;

    private static final long SIZECTL;

    private static final long ABASE;
    private static final int ASHIFT;
    private static final long BASECOUNT;

    private transient volatile long baseCount;

    private transient volatile CounterCell[] counterCells;

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            U = (Unsafe) unsafeField.get(null);
            Class<?> k = ConcurrentHashMap.class;
            SIZECTL = U.objectFieldOffset
                    (ConcurrentHashMap.class.getDeclaredField("sizeCtl"));
            BASECOUNT = U.objectFieldOffset
                    (k.getDeclaredField("baseCount"));
            //用于CAS计算偏移量 暂时不理解原理
            Class<?> ak = Node[].class;
            ABASE = U.arrayBaseOffset(ak);
            int scale = U.arrayIndexScale(ak);
            if ((scale & (scale - 1)) != 0)
                throw new Error("data type scale not a power of two");
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
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

    @sun.misc.Contended
    static final class CounterCell {
        volatile long value;

        CounterCell(long x) {
            value = x;
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
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                if (casTabAt(tab, i, null,
                        new Node<K, V>(hash, key, value, null)))
                    break;
            }
        }
        addCount(1L, binCount);
        return null;
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

    /**
     * CAS获取数组i下标对应的元素 暂时不理解为什么是根据ASHIFT和ABASE来取
     *
     * @param tab
     * @param i
     * @param <K>
     * @param <V>
     * @return
     */
    static final <K, V> Node<K, V> tabAt(Node<K, V>[] tab, int i) {
        return (Node<K, V>) U.getObjectVolatile(tab, ((long) i << ASHIFT) + ABASE);
    }

    /**
     * CAS更新数组下标对应的元素
     *
     * @param tab
     * @param i
     * @param c
     * @param v
     * @param <K>
     * @param <V>
     * @return
     */
    static final <K, V> boolean casTabAt(Node<K, V>[] tab, int i,
                                         Node<K, V> c, Node<K, V> v) {
        return U.compareAndSwapObject(tab, ((long) i << ASHIFT) + ABASE, c, v);
    }


    /**
     * 递增数量
     */
    private final void addCount(long x, int check) {
        ConcurrentHashMap.CounterCell[] as;
        long b, s = 0;
        if ((as = counterCells) != null ||
                !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {

        }
//            java.util.concurrent.ConcurrentHashMap.CounterCell a;
//            long v;
//            int m;
//            boolean uncontended = true;
//            if (as == null || (m = as.length - 1) < 0 ||
//                    (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
//                    !(uncontended =
//                            U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
//                fullAddCount(x, uncontended);
//                return;
//            }
//            if (check <= 1)
//                return;
//            s = sumCount();
//        }
        if (check >= 0) {
            Node<K, V>[] tab, nt;
            int n, sc;
            while (s >= (long) (sc = sizeCtl) && (tab = table) != null &&
                    (n = tab.length) < MAXIMUM_CAPACITY) {

            }
//                int rs = resizeStamp(n);
//                if (sc < 0) {
//                    if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
//                            sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
//                            transferIndex <= 0)
//                        break;
//                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
//                        transfer(tab, nt);
//                } else if (U.compareAndSwapInt(this, SIZECTL, sc,
//                        (rs << RESIZE_STAMP_SHIFT) + 2))
//                    transfer(tab, null);
//                s = sumCount();
//            }
//        }
        }
    }

    /**
     * 源码阅读记录:
     * 1 完成阅读一个元素的添加过程
     *
     * 下一个目标:
     * 2 阅读哈希冲突时元素如何添加
     * 3 阅读扩容过程
     * @param args
     */
    public static void main(String[] args) {
        java.util.concurrent.ConcurrentHashMap concurrentHashMap = new java.util.concurrent.ConcurrentHashMap();
        concurrentHashMap.put("a", "1");
    }
}