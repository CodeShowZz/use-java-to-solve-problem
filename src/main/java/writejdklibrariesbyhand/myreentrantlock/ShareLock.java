package writejdklibrariesbyhand.myreentrantlock;

/**
 * 共享锁实现
 * 1.有公平和非公平两种实现
 *
 * @author junlin_huang
 * @create 2021-02-02 下午11:47
 **/

public class ShareLock {


    /**
     * 负责定义线程之间如何进行锁的抢占
     */
    private Sync sync;


    public ShareLock(int require) {
        sync = new FairSync(require);
    }

    public ShareLock(boolean fair, int require) {
        sync = (fair == true ? new FairSync(require) : new NonFairSync(require));
    }


    public void lock() {
        sync.acquireShared();
    }

    public void unlock() {
        sync.releaseShared();
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

        public FairSync(int require) {
            super.setState(require);
        }

        @Override
        public boolean tryAcquireShared() {
            int state = getState();
            if (state > 0) {
                if ((isQueueEmpty() || isFirstNode()) && compareAndSetState(state, state - 1)) {
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

        public NonFairSync(int require) {
            super.setState(require);
        }

        @Override
        public boolean tryAcquireShared() {
            return tryNonfairAcquireShared();
        }

        /**
         * 当锁空出时 线程就可以竞争锁,不需要考虑队列最前面的线程,如果进入队列了就只能排队等着了
         * 也就是说排队之前还有机会竞争一下锁,竞争不到才入队
         *
         * @return
         */
        public final boolean tryNonfairAcquireShared() {
            int state = getState();
            if (state > 0) {
                if (compareAndSetState(state, state - 1)) {
                    return true;
                }
            }
            return false;
        }

    }


}