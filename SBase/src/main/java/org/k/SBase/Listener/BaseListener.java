package org.k.SBase.Listener;

/**
 * 更新任意消息会回调到这个方法
 * 任何实现了这个接口的类都会收到监听对应类型通知
 */
public interface BaseListener<T,E> {

    /**
     * @param topic
     * @param e
     */
    void onCallback(T topic, E e);
}
