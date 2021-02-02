package writejdklibrariesbyhand.myreentrantlock;

/**
 * @author junlin_huang
 * @create 2021-02-03 上午12:25
 **/

public class ShareLockTest {

    public static void main(String[] args) {
        ShareLock shareLock = new ShareLock(5);
        for (int i = 1; i < 100; i++) {
            new Thread(() -> {
                shareLock.lock();
                String name = Thread.currentThread().getName();
                System.out.println("线程" + name + "获得了锁");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shareLock.unlock();
                System.out.println("线程" + name + "释放了锁");
            }).start();
        }
    }



}