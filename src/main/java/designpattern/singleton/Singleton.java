package designpattern.singleton;

/**
 * @author junlin_huang
 * @create 2021-03-22 上午12:57
 **/

public class Singleton {

    private static Singleton singleton;

    private Singleton() {

    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}