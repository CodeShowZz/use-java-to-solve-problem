package multithread.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UseUnsafeToSimulateAtomicInteger {

    public static void main(String[] args) throws InterruptedException {

        MyAtomicInteger myAtomicInteger = new MyAtomicInteger();
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

class MyAtomicInteger {
    private volatile int value;

    private long valueOffset;

    private Unsafe unsafe;

    {
        try {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
            valueOffset = unsafe.objectFieldOffset(this.getClass().getDeclaredField("value"));
        } catch (Exception e) {

        }
    }


    public MyAtomicInteger() {

    }

    public void increment() {
        int currentValue;
        do {
            currentValue = unsafe.getIntVolatile(this, valueOffset);
        } while (!unsafe.compareAndSwapInt(this, valueOffset, currentValue, currentValue + 1));

    }

    public int get() {
        return value;
    }
}
