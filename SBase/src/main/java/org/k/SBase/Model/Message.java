/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * E-mail: kennychaos7@gmail.com
 */

package org.k.SBase.Model;

/**
 * Created by Kenny on 18-7-14.
 */
public abstract class Message<T> {

    /**
     * data     = 1
     * state    = 2
     */
    private int mMessageType = -1;

    /**
     * 检测这个topic是否在已经注册的监听topicList中
     */
    private int mMessageTopic = -1;

    private T t;

    /**
     *
     * @param o
     * @return
     */
    public abstract int putData(T o);

    /**
     *
     * @return
     */
    public abstract T getData();

}