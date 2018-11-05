package org.k.SBase;


import org.k.SBase.Listener.BaseListener;
import org.k.SBase.Model.Message;
import org.k.SBase.Model.MessageQueue;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.k.SBase.SObserverListener.Notification;
import static org.k.SBase.SObserverListener.UPDATE;

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
    private static HashSet<Integer> TYPES = new HashSet<>();

    /*
        监听者列表
     */
    private Map<Integer,BaseListener> mListenerMap = Collections.synchronizedMap(new HashMap<>());

    /*

     */
    private MessageQueue<Message<HashMap<Integer,Object>>> mMessageQueue = new MessageQueue<>();

    private static SObserverWatcher sWatcher = null;

    public static SObserverWatcher getInstance() {
        synchronized (SObserverWatcher.class) {
            if (sWatcher == null) {
                sWatcher = new SObserverWatcher();
                TYPES.add(UPDATE);
                TYPES.add(Notification);
            }
            return sWatcher;
        }
    }

    void registerListener(int type, SObserverListener listener) {
        if (!mListenerMap.containsKey(type))
            mListenerMap.put(type,listener);
    }

    void unregisterListener(int type, SObserverListener listener) {
        if (mListenerMap.containsKey(type))
            mListenerMap.remove(type);
    }

    public void addObserverType(int type) {
        if (!TYPES.contains(type)) {
            TYPES.add(type);
        }
    }

    public void addMessage(Message<HashMap<Integer,Object>> msg)
    {
        if (msg != null)
            mMessageQueue.add(msg);
        else
            throw new IllegalArgumentException("msg is null");
    }

    /**
     *
     * @param args
     */
    public synchronized void Notify(int type, Object... args) {
        synchronized (mListenerMap) {
            if (TYPES.contains(type) && args != null) {

            }
        }
    }

}
