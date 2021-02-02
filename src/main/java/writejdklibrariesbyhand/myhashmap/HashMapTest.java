package writejdklibrariesbyhand.myhashmap;


/**
 * @author junlin_huang
 * @create 2021-01-29 上午12:24
 **/

public class HashMapTest {

    //   e.hash & (newCap - 1)
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            hashMap.put(String.valueOf(i), String.valueOf(i));
        }
    }
}