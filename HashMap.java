package multithread.hashmap;

/**
 * 1 理论:当一个数是 2^n 时, 任意整数对2^n取模等效于: h % 2^n = h & (2^n -1),而或操作性能比取模性能操作好
 * 所以hashMap中table的大小总是2^n,即使你初始化大小的时候不满足这个条件,内部也会将它变成大于等于这个大小的最近的2^n
 * 2 不考虑容量大于Integer.MAXVALUE
 **/
public class HashMap<K, V> {

    /**
     * 默认初始化大小为16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 默认负载因子大小为0.75
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 负载因子
     */
    final float loadFactor;

    /**
     * 数组的大小 table.length
     */
    private int threshold;

    /**
     * 存放节点的数组
     */
    Node[] table;

    /**
     * 节点
     *
     * @param <K>
     * @param <V>
     */
    class Node<K, V> {
        /**
         * 存储的键
         */
        K key;
        /**
         * 存储的值
         */
        V value;
        /**
         * 下一个节点
         */
        Node next;

        /**
         * 计算出的哈希值
         */
        int hash;
    }

    /**
     * 默认数组大小为16  默认负载因子为0.75
     */
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    /**
     * 指定初始大小 使用默认负载因子0.75
     *
     * @param initialCapacity
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    // 指定初始大小和负载因子
    public HashMap(int initialCapacity, float loadFactor) {
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    /**
     * 根据初始容量找到大于等于它的最近的2的幂
     * 这个算法我不会
     *
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n;
    }

    /**
     * 计算哈希值 为了给节点分配位置
     * 将int的高16位的低16位进行异或,对低位进行了扰动,使计算出来的值更加均匀,具体原理这里不深究,原理我也不懂.
     *
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); // 将int的高16位的低16位进行异或,对低位进行了扰动
    }

    /**
     * 扩容
     * 1 表存在,并且当前表大小大于16 扩容为原来的2倍
     * 2 表不存在,初始化时指定了threshold,则大小为tableSizeFor(初始大小)
     * 3 表不存在,初始化时没有指定threshold,则使用默认值
     *
     * @return
     */
    final Node<K, V>[] resize() {
        Node[] oldTab = table;
        int oldCap = (oldTab == null ? 0 : oldTab.length);
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0 && oldCap >= DEFAULT_INITIAL_CAPACITY) { // 表存在,并且当前表大小大于16 扩容为原来的2倍
            newThr = oldThr << 1;
        } else if (oldThr > 0) { // 表不存在,初始化时指定了threshold,则大小为tableSizeFor(初始大小)
            newCap = oldThr;
        } else {  // 表不存在,初始化时没有指定threshold,则使用默认值
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        return null;
    }
}
