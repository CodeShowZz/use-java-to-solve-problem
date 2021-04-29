package lock.distribute;

import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * Redis分布式锁(获取有超时限制 锁本身也带有超时限制)
 * @author junlin_huang
 * @create 2021-04-29 下午8:30
 **/

public class RedisDistributeLock {

    public String tryAcquireLockWithTimeout(Jedis jedis,String lockName, long acquireTimeout, long lockTimeout) {
        String lockKey = "lock:" + lockName;
        long timeout = System.currentTimeMillis() + acquireTimeout;
        String identify = UUID.randomUUID().toString();
        while(System.currentTimeMillis() < timeout) {
            if(jedis.setex(lockKey,(int)lockTimeout,identify) == "1") {
                return identify;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}