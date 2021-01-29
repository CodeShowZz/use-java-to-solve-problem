package writejdklibrariesbyhand.myhashmap;

/**
 * 写一个简易版的HashMap
 * 1 理论:当一个数是 2^n 时, 任意整数对2^n取模等效于: h % 2^n = h & (2^n -1),而或操作性能比取模性能操作好
 * 所以hashMap中table的大小总是2^n,即使你初始化大小的时候不满足这个条件,内部也会将它变成大于等于这个大小的最近的2^n
 * 2 不考虑容量大于Integer.MAXVALUE
 * 3 去除部分移位 或操作,虽然作者本意是提高性能,但
 **/
public class HashMap<K, V> {

    /**
     * 默认初始化大小为16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * 默认负载因子大小为0.75
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 负载因子
     */
    final float loadFactor;

    /**
     * capacity * loadFactor
     */
    private int threshold;

    /**
     * 数组容量
     */
    private int capacity;

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
        capacity = DEFAULT_INITIAL_CAPACITY;
        threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    // 指定初始大小和负载因子
    public HashMap(int initialCapacity) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.capacity = tableSizeFor(initialCapacity);
        this.threshold = capacity * DEFAULT_INITIAL_CAPACITY;
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
        if (table == null) {
            table = new Node[capacity];
        } else {
            expand();
            Node[] oldTab = table;
            Node[] newTab = new Node[capacity];
            for (int i = 0; i < oldTab.length; i++) {
                Node<K, V> node;
                if ((node = oldTab[i]) != null) {
                    oldTab[i] = null;
                    //说明该节点没有下一个节点,直接复制到新表中
                    if (node.next == null) {
                        int index = node.hash % capacity;
                        newTab[index] = node;
                    } else { //原链表至少有两个节点 这里暂不考虑链表元素多的时候 转换成红黑树的情况
                        /**
                         * 旧链表里的每一个节点其实都是需要计算哈希,然后对新的容量
                         * 求模来计算存放的位置,在这个时候其实是有规律的,它的存放位置只有以下两种情况:
                         * 1. 还是原来的位置
                         * 2. 原来的位置 + 新的容量 / 2
                         *
                         *
                         * 所以这里用loHead和loTail表示旧位置
                         * 用hiHead和hiTail表示新位置
                         */
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            if (node.hash > 0) {

                            }

                            next = node.next;
                        } while (true);
                    }
                }
            }
        }
        return table;
    }

    private void expand() {
        capacity = capacity * 2;
        threshold = (int) (capacity * loadFactor);
    }
}
