package com.naver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2020/3/17.
 */
public class CachedData {

    private Map<String, CachedData> cacheMap;
    private String key;
    private Long expireTime;
    private ReentrantReadWriteLock lock;

    public CachedData() {
        this.cacheMap = new HashMap<>();
        this.lock = new ReentrantReadWriteLock();
        this.key = UUID.randomUUID().toString();
        this.expireTime = System.currentTimeMillis() + 1000 * 60;
    }

    public CachedData(String key, Long expireTime) {
        this.key = key;
        this.expireTime = expireTime;
    }

    public void readLock() {
        lock.readLock().lock();
    }

    public void unReadLock() {
        lock.readLock().unlock();
    }

    public void writeLock() {
        lock.writeLock().lock();
    }

    public void unWriteLock() {
        lock.writeLock().unlock();
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    private void processCachedData() {
        long now = System.currentTimeMillis();
        try {
            readLock();
            for (Map.Entry<String, CachedData> entry : cacheMap.entrySet()) {
                if (entry.getValue().getExpireTime() > now) {
                    try {
                        writeLock();
                        //从数据源加载数据
                        CachedData cacheData = get(entry.getKey());
                        cacheMap.put(cacheData.key, cacheData);
                    } finally {
                        unWriteLock();
                    }
                }
                System.out.println("处理数据");
            }
        } finally {
            unReadLock();
        }
    }

    private CachedData get(String key) {
        return new CachedData(key, System.currentTimeMillis() * 1000 * 6);
    }
}
