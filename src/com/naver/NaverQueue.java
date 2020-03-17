package com.naver;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2020/3/17.
 */
public class NaverQueue {
    private Object[] array;
    private int pushIndex = 0;
    private int popIndex = 0;
    private int elementCount;
    private ReentrantLock lock;


    public NaverQueue(int size) {
        this.array = new Object[size];
        this.lock = new ReentrantLock();
    }

    private void push(Object object) {
        if (object == null) {
            System.out.println("元素为空，不能添加");
            return;
        }
        try {
            lock.lock();
            if (elementCount == array.length) {
                System.out.println("队列元素已满 不能添加");
                return;
            }
            array[pushIndex] = object;
            if (++pushIndex == array.length) {
                pushIndex = 0;
            }
            elementCount++;
        } finally {
            lock.unlock();
        }
    }

    public Object pop() {

        try {
            lock.lock();
            if (elementCount == 0) {
                System.out.println("队列没有元素已满 ");
                return null;
            }
            Object element = array[popIndex];
            if (++popIndex == array.length) {
                popIndex = 0;
            }
            elementCount--;
            return element;
        } finally {
            lock.unlock();
        }
    }

    public boolean empty() {
        return elementCount == 0;
    }


}
