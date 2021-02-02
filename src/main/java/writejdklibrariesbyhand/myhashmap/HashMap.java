package writejdklibrariesbyhand.myhashmap;

/**
 * 写一个简易版的HashMap
 * 1 理论:当一个数是 2^n 时, 任意整数对2^n取模等效于: h % 2^n = h & (2^n -1),而或操作性能比取模性能操作好
 * 所以hashMap中table的大小总是2^n,即使你初始化大小的时候不满足这个条件,内部也会将它变成大于等于这个大小的最近的2^n
 * 2 不考虑容量大于Integer.MAXVALUE
 * 3 去除部分移位或操作,虽然作者本意是提高性能,但这样写可能不太直观,为了可读性,这里将去除一部分.
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
     * 存放元素的数量
     */
    transient int size;

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


        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
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
     * 存储key,value
     * 1.表空则初始化
     * 2.取模,找到插入位置,有可能有三种情况
     * - 该位置没有节点
     * - 该位置有节点,也就是链表的结构(在我的实现中暂时不考虑红黑树)
     * 3 当哈希冲突时,有以下两种情况:
     * - 如果key是一样的,我们认为是同一元素,如果value不一样,则替换原节点的值.
     * - 如果key不是一样的,我们最终放在链表的尾部
     * 4.计算元素数量,如果超出阈值,则扩容(表中元素数量size大于threshold)
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        int hash = hash(key);
        V oldValue = null;
        if (table == null) {
            resize();
            int index = hash % capacity;
            table[index] = newNode(hash, key, value, null);
        } else {
            int index = hash % capacity;
            Node<K, V> node = table[index];
            if (node == null) {
                table[index] = newNode(hash, key, value, null);
            } else {
                //这里用一个死循环,因为这里的代码最终一定会break或者return
                for (; ; ) {
                    //如果链表中有相同的key,我们认为是同一元素,替换原节点的值(可能value是一样的)
                    if ((key != null && key.equals(node.key))) {
                        oldValue = node.value;
                        node.value = value;
                        return oldValue;
                    }
                    //到达链表尾部
                    if (node.next == null) {
                        node.next = newNode(hash, key, value, null);
                        break;
                    } else {
                        node = node.next;
                    }
                }
            }
        }
        //除非哈希冲突key值一致,这个时候上面的代码直接return,size不用加一,也就不用判断是否需要扩容,否则其余情况都会有新元素
        size++;
        if (size > threshold) {
            resize();
        }
        return oldValue;
    }

    Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }

    /**
     * 扩容
     * 1 表存在,并且当前表大小大于16 扩容为原来的2倍
     * 2 表不存在,初始化时指定了初始大小,则大小为tableSizeFor(初始大小)
     * 3 表不存在,初始化时没有指定初始大小,则使用默认值
     *
     * @return
     */
    final Node<K, V>[] resize() {
        if (table == null) {
            table = new Node[capacity];
        } else {
            int oldCapacity = capacity;
            expand(); //扩容
            Node[] oldTab = table;
            Node[] newTab = new Node[capacity];
            for (int i = 0; i < oldTab.length; i++) {
                Node<K, V> node = oldTab[i];
                //该位置有节点才处理
                if (node != null) {
                    oldTab[i] = null;
                    //说明该节点没有下一个节点,直接复制到新表中
                    if (node.next == null) {
                        int index = node.hash % capacity;
                        newTab[index] = node;
                    } else { //原链表至少有两个节点 这里暂不考虑链表元素多的时候 转换成红黑树的情况
                        /**
                         * 旧链表里的每一个节点其实都是需要计算哈希,然后对新的容量
                         * 取模来计算存放的位置,在这个时候其实是有规律的,它的存放位置只有以下两种情况:
                         * 1. 还是原来的位置(我们称为低位)
                         * 2. 原来的位置 + 旧的容量(我们称为高位)
                         * 这个规律可以自己举个例子模拟一下
                         * 所以这里用loHead和loTail表示旧位置
                         * 用hiHead和hiTail表示新位置
                         */
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        int loIndex = node.hash % oldCapacity;
                        int hiIndex = node.hash % oldCapacity + oldCapacity;
                        do {
                            //还是原来的位置
                            if (node.hash % capacity == loIndex) {
                                if (loHead == null) {
                                    loHead = loTail = node;
                                } else {
                                    loTail.next = node;
                                    loTail = node;
                                }
                            } else { //原来的位置 + 旧的容量 其实就是highIndex
                                if (hiHead == null) {
                                    hiHead = hiTail = node;
                                } else {
                                    hiTail.next = node;
                                    hiTail = node;
                                }
                            }
                        } while ((node = node.next) != null);

                        //将低位链表和高位链表赋值到对应位置
                        if (loHead != null) {
                            newTab[loIndex] = loHead;
                        }
                        if (hiTail != null) {
                            newTab[hiIndex] = hiHead;
                        }
                    }
                }
            }
            table = newTab;
        }
        return table;
    }

    /**
     * 表扩大为原来的2倍 重新计算阈值
     */
    private void expand() {
        capacity = capacity * 2;
        threshold = (int) (capacity * loadFactor);
    }
}
