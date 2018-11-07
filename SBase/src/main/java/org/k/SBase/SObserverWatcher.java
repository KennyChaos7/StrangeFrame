package org.k.SBase;


import org.k.SBase.Listener.BaseListener;
import org.k.SBase.Model.Message;
import org.k.SBase.Model.MessageQueue;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import static org.k.SBase.SObserverListener.MSG_TOPIC_DATA_UPDATE;
import static org.k.SBase.SObserverListener.MSG_TOPIC_Notification;

/*
 * 管理所有观察者Observer
 * 负责将所有的Activity和Fragment对应的观察者Observer
 */
public class SObserverWatcher {
    private final String TAG = getClass().getSimpleName().toLowerCase();
    /*
        通知类型数组
        可以自己新加
     */
    private volatile static HashSet<Integer> MSG_TOPICS = new HashSet<>();

    /*
        监听者列表
     */
    private volatile List<SListenerModel> mListenerList = Collections.synchronizedList(new Vector<>());

    /*
        消息队列
     */
    private volatile MessageQueue<Message<HashMap<String, Object>>> mMessageQueue = new MessageQueue<>();

    private static SObserverWatcher sWatcher = null;

    public static SObserverWatcher getInstance() {
        synchronized (SObserverWatcher.class) {
            if (sWatcher == null) {
                sWatcher = new SObserverWatcher();
                MSG_TOPICS.add(MSG_TOPIC_DATA_UPDATE);
                MSG_TOPICS.add(MSG_TOPIC_Notification);
            }
            return sWatcher;
        }
    }

    /*
     * 注册观察者
     */
    public int registerListener(int topic, SObserverListener listener) {
        SListenerModel sListenerModel = new SListenerModel(topic, listener);
        if (!mListenerList.contains(sListenerModel)) {
            mListenerList.add(sListenerModel);
            return 0;
        } else
            /*
                没有找到对应的主题
             */
            return 1;
    }

    public int unregisterListener(int topic, SObserverListener listener) {
        SListenerModel sListenerModel = new SListenerModel(topic, listener);
        if (mListenerList.contains(sListenerModel)) {
            mListenerList.remove(sListenerModel);
            return 0;
        } else
            /*
                没有找到对应主题或监听者
             */
            return 1;
    }

    public void addObserverTopic(int topic) {
        if (!MSG_TOPICS.contains(topic)) {
            MSG_TOPICS.add(topic);
        }
    }

    /**
     * @param msg 压入队列中
     */
    public void putMessage(Message<HashMap<String, Object>> msg) {
        if (msg != null)
            /*
                可扩容的添加方式
             */
            mMessageQueue.add(msg);
        else
            throw new IllegalArgumentException("msg is null");
    }

    /**
     * 调用这个方法后, 将自动把队列中消息进行同步更新到各个监听者中
     * 消息和对应的topic会存在一个hashMap中
     * 校验时, 需先确定对应的hashMap存有topic主题参数, 然后再在此hashMap中根据topic主题参数取出对应的消息体
     */
    public synchronized void Notify() {
        synchronized (this) {
            while (!mMessageQueue.isEmpty()) {
                HashMap<String, Object> msg = mMessageQueue.poll().getData();
                int msg_topic = msg.get("topic") != null ? (int) msg.get("topic") : -1;
                if (msg_topic != -1 && msg.get(msg_topic) != null) // 证明此msg存在
                {
                    for (SListenerModel listenerModel : mListenerList) {
                        if (listenerModel.getTopic() == msg_topic) {
                               /*
                                    回调
                                */
                            listenerModel.getBaseListener().onListen(msg_topic, msg.get(msg_topic));
                        }
                    }
                }
            }
        }
    }

    private class SListenerModel {
        private int topic;
        private BaseListener mBaseListener;

        SListenerModel(int topic, BaseListener listener) {
            this.topic = topic;
            this.mBaseListener = listener;
        }

        int getTopic() {
            return topic;
        }

        BaseListener getBaseListener() {
            return mBaseListener;
        }
    }
}
