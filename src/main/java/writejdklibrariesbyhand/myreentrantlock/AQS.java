package writejdklibrariesbyhand.myreentrantlock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.LockSupport;

/**
 * AQS工具类 专门负责提供队列和队列状态的维护
 *
 * @author junlin_huang
 * @create 2021-01-27 下午10:06
 **/

public abstract class AQS {

    /**
     * 锁状态
     */
    private volatile int state;

    /**
     * 队列头节点
     */
    private volatile Node head;

    /**
     * 对列尾节点
     */
    private volatile Node tail;

    /**
     * unsafe
     */
    private static final Unsafe unsafe;
    /**
     * state偏移量
     */
    private static final long stateOffset;
    /**
     * head偏移量
     */
    private static final long headOffset;
    /**
     * tail偏移量
     */
    private static final long tailOffset;

    /**
     * 获取unsafe 使用unsafe获取字段偏移量
     */
    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
            stateOffset = unsafe.objectFieldOffset
                    (AQS.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (AQS.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AQS.class.getDeclaredField("tail"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * 队列节点
     */
    class Node {

        /**
         * 节点的上一个节点
         */
        private volatile Node prev;

        /**
         * 节点的下一个节点
         */
        private volatile Node next;

        /**
         * 节点指代的线程
         */
        private volatile Thread thread;

    }

    /**
     * 获取状态
     *
     * @return
     */
    public int getState() {
        return state;
    }

    /**
     * 每次只有一个线程能够执行完这个程序逻辑,其他线程会阻塞在其中的某个环节中
     */
    public final void acquire() {
        if (tryAcquire()) {
            return;
        }
        acquireQueued(enq());
    }

    /**
     * 每次可以由多个线程能够执行完这个程序逻辑,其他线程会阻塞在其中的某个环节中
     */
    public final void acquireShared() {
        if (tryAcquireShared()) {
            return;
        }
        acquireQueuedShared(enq());
    }

    /**
     * 释放锁,并唤醒队列中第一个等待的线程
     */
    public final void release() {
        state = 0;
        unparkSuccessor();
    }


    /**
     * 释放锁,但有可能有多个线程同时释放,所以要使用CAS,释放成功后唤醒伪节点后的线程
     */
    public final void releaseShared() {
        for (; ; ) {
            if (compareAndSetState(state, state + 1)) {
                break;
            }
        }
        unparkSuccessor();
    }


    /**
     * 唤醒队列中第一个等待的线程
     */
    private void unparkSuccessor() {
        if (head != null && head.next != null) {
            LockSupport.unpark(head.next.thread);
        }
    }


    /**
     * 由子类负责实现如何抢占独占锁
     *
     * @return
     */
    public boolean tryAcquire() {
        throw new UnsupportedOperationException();
    }

    /**
     * 由子类负责实现如何抢占共享锁
     */
    public boolean tryAcquireShared() {
        throw new UnsupportedOperationException();
    }


    /**
     * 队列是否还没初始化
     *
     * @return
     */
    public boolean isQueueEmpty() {
        return head == null || head.next == null;
    }

    /**
     * 是否该线程是第一个等待的线程
     *
     * @return
     */
    public boolean isFirstNode() {
        return head.next.thread == Thread.currentThread();
    }

    /**
     * 如果没有队列 则初始化队列 然后将节点加入到队列尾部,否则直接加入队列尾部
     *
     * @return
     */
    private Node enq() {
        Node node = new Node();
        node.thread = Thread.currentThread();
        for (; ; ) {
            Node t = tail;
            if (t == null) { //还没初始化队列,建立队列
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return node;
                }
            }
        }
    }


    /**
     * 节点尝试获取锁,此时只有伪节点的下一个节点有可能能够获取锁成功,如果这次没有获取成功,则挂起.
     * 等获得锁的线程释放锁后,会唤醒这个节点.其余节点都是进入挂起状态.
     * 等伪节点的下一个节点获取到锁了,我们就把它变成新的伪节点,其实就相当于移出队列.它的下一个节点就成为了第一个等待锁的节点.
     *
     * @param node
     */
    final void acquireQueued(Node node) {
        for (; ; ) {
            final Node p = node.prev;
            if (p == head && tryAcquire()) {
                setHead(node);
                return;
            }
            park();
        }
    }

    /**
     * 节点尝试获取锁,此时只有伪节点的下一个节点有可能能够获取锁成功,如果这次没有获取成功,则挂起.
     * 但是在共享模式下,多个线程可以共享一个锁,所以当伪节点再次更换时,它的下一个节点应该立即被唤醒来尝试获取锁.
     * 等伪节点的下一个节点获取到锁了,我们就把它变成新的伪节点,其实就相当于移出队列.它的下一个节点就成为了第一个等待锁的节点.
     *
     * @param node
     */
    final void acquireQueuedShared(Node node) {
        for (; ; ) {
            final Node p = node.prev;
            if (p == head && tryAcquireShared()) {
                setHead(node);
                unparkSuccessor();
                return;
            }
            park();
        }
    }


    /**
     * 将头节点设置为该节点
     *
     * @param node
     */
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    /**
     * 线程挂起
     */
    private void park() {
        LockSupport.park(this);
    }

    /**
     * CAS设置状态
     *
     * @param expect
     * @param update
     * @return
     */
    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    /**
     * CAS设置队列头部
     *
     * @param update
     * @return
     */
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    /**
     * CAS设置队列尾部
     *
     * @param expect
     * @param update
     * @return
     */
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }
}
