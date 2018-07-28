/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 * E-mail: kennychaos7@gmail.com
 */

package org.k.strangeframe.Model;


import java.util.NoSuchElementException;

/**
 * Created by Kenny on 18-7-14.
 */
public class MessageQueue<T> implements Queue<T> {

    private int max_size = 128;
    private int size = 0;
    private int front = -1, rear = -1;
    private T[] mTS;

    public MessageQueue() {
        this.front = 0;
        this.rear = 0;
        this.size = max_size;
        mTS = (T[]) new Object[max_size];
    }

    public MessageQueue(int size) {
        this.front = 0;
        this.rear = 0;
        this.size = size;
        mTS = (T[]) new Object[size];
    }

    /****************** Queue ******************/
    /**
     * 可扩容添加方式
     *
     * @param t
     * @return
     */
    @Override
    public boolean add(T t) {

        if (t == null)
            throw new NullPointerException("this data is null");

        /**
         * 扩容
         */
        if (size >= max_size)
            ensureCapacity(max_size * 2 + 1);

        mTS[rear] = t;
        rear++;
        size++;
        return true;
    }

    /**
     * 不可扩容添加方式
     *
     * @param t
     * @return
     */
    @Override
    public boolean offer(T t) {

        if (t == null)
            throw new NullPointerException("this data is null");
        if (front == (rear + 1) % mTS.length)
            throw new IllegalArgumentException("this queue is full");

        mTS[rear] = t;
        rear = (rear + 1) % mTS.length;
        return true;
    }

    /**
     * 出队，执行删除操作，会抛出异常
     *
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public T remove() {
        if (isEmpty())
            throw new NoSuchElementException("this queue is empty");
        return poll();
    }

    @Override
    public T poll() {
        T temp = mTS[this.front];
        front = (front + 1) % mTS.length;
        size--;
        return temp;
    }

    @Override
    public T element() {
        if (isEmpty())
            throw new IllegalArgumentException("this queue is empty");
        return peek();
    }

    /**
     * 返回队头元素，不执行删除
     *
     * @return
     */
    @Override
    public T peek() {
        return isEmpty() ? null : mTS[front];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    @Override
    public void clear() {
        int i = front;
        while (i != rear) {
            mTS[i] = null;
            i = (i + 1) % mTS.length;
        }
        front = 0;
        rear = 0;
    }

    @SuppressWarnings("uncheck")
    private void ensureCapacity(int capacity) {

        if (capacity < size)
            return;

        T[] old = mTS;
        mTS = (T[]) new Object[capacity];
        int i = front;
        int j = 0;
        while (i != rear) {
            mTS[j++] = old[i];
            i = (i + 1) % old.length;
        }
        front = 0;
        rear = i;
    }

}
