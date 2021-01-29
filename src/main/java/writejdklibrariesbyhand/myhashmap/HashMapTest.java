package writejdklibrariesbyhand.myhashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author junlin_huang
 * @create 2021-01-29 上午12:24
 **/

public class HashMapTest {

    //   e.hash & (newCap - 1)
    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "2");
        hashMap.put("1", "3");
        System.out.println(hashMap);
        System.out.println(100 & 64);
    }
}