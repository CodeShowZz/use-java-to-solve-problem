package writejdklibrariesbyhand.myreentrantlock


/**
 * 可重入锁
 * 1.实现公平(使用FairSync)和非公平(使用NonFairSync)版本,借助AQS维护状态和队列
 * 2.暂时还未实现可重入
 **/
public class ReentrantLock {

    /**
     * 负责定义线程之间如何进行锁的抢占
     */
    private Sync sync;

    public ReentrantLock() {
        this.sync = new NonFairSync();
    }

    public ReentrantLock(boolean fair) {
        sync = (fair == true ? new FairSync() : new NonFairSync());
    }

    /**
     * 获取锁
     */
    public void lock() {
        sync.acquire();
    }

    /**
     * 解锁
     */
    public void unlock() {
        sync.release();
    }

    /**
     * 负责定义线程之间如何进行锁的抢占
     */
    public abstract class Sync extends AQS {

    }

    /**
     * 公平版抢占
     */
    public class FairSync extends Sync {
        @Override
        public boolean tryAcquire() {
            int state = getState();
            if (state == 0) {
                if ((isQueueEmpty() || isFirstNode()) && compareAndSetState(0, 1)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 非公平版抢占
     */
    public class NonFairSync extends Sync {
        @Override
        public boolean tryAcquire() {
            return tryNonfairAcquire();
        }

        /**
         * 当锁空出时 线程就可以竞争锁,不需要考虑队列最前面的线程,如果进入队列了就只能排队等着了
         * 也就是说排队之前还有机会竞争一下锁,竞争不到才入队
         *
         * @return
         */
        public final boolean tryNonfairAcquire() {
            int state = getState();
            if (state == 0) {
                if (compareAndSetState(0, 1)) {
                    return true;
                }
            }
            return false;
        }

    }
}
