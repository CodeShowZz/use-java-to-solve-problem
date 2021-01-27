package writejdklibrariesbyhand.myatomicinteger;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 使用CAS来编写一个简易版AtomicInteger
 *
 * @author junlin_huang
 * @create 2021-01-28 上午1:48
 **/

public class AtomicInteger {

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


    public AtomicInteger() {

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