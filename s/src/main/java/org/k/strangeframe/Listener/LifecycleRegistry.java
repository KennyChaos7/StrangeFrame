/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * E-mail: kennychaos7@gmail.com
 */

package org.k.strangeframe.Listener;

import java.util.HashMap;

/**
 * Created by Kenny on 18-7-19.
 */
public class LifecycleRegistry extends Lifecycle {
    private State mCurrentState = null;
    private HashMap<LifecycleOwner,LifecycleObserver> mLifecycleObserverList = new HashMap<>();

    @Override
    public void addObserver(LifecycleObserver observer) {
        if (!mLifecycleObserverList.containsKey(observer))
        {
//            mLifecycleObserverList.put(observer);
        }
    }

    @Override
    public void removeObserver(LifecycleObserver observer) {
//        if (mLifecycleObserverList.size() > 0 && mLifecycleObserverList.contains(observer))
//        {
//            mLifecycleObserverList.remove(observer);
//        }
    }

    @Override
    public State getCurrentState() {
        return mCurrentState;
    }

    public int getObserverCount(){
        return mLifecycleObserverList.size();
    }

    public void markState(State state)
    {

    }

    public void handleLifecycleEvent(Event event)
    {

    }
}
