package writejdklibrariesbyhand.myatomicinteger;

/**
 * 测试简易版AtomicInteger 开启十个线程 被个线程递增100次  最终结果为1000 则说明实现成功
 *
 * @author junlin_huang
 * @create 2021-01-28 上午1:50
 **/

public class AtomicIntegerTest {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger myAtomicInteger = new AtomicInteger();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    myAtomicInteger.increment();
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(myAtomicInteger.get());
    }
}