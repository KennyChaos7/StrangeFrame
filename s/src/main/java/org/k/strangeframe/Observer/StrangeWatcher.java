package org.k.strangeframe.Observer;


import org.k.strangeframe.Listener.StrangeListener;
import org.k.strangeframe.S_STATE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by Kenny on 18-7-30.
 * 管理所有观察者Observer
 * 负责将所有的Activity和Fragment对应的观察者Observer
 */
public class StrangeWatcher {
    private final String TAG = getClass().getSimpleName().toLowerCase();

    private List<StrangeListener> mListenerArrayList = Collections.synchronizedList(new Vector<StrangeListener>());

    public int registerListener(StrangeListener listener)
    {
        if (listener == null)
            return S_STATE.Failed;

        for (StrangeListener strangeListener : mListenerArrayList)
        {
            if (listener == strangeListener)
                return S_STATE.HadRegistered;
            else
                {
                    mListenerArrayList.add(listener);
                    return S_STATE.RegisterSuccess;
            }
        }
        return S_STATE.Failed;
    }

    public int unregisterListener(StrangeListener listener)
    {
        return mListenerArrayList.remove(listener) ? S_STATE.UnregisterSuccess : S_STATE.Failed;
    }

    private synchronized void reflex(Object type,Object... arg)
    {

    }
}

enum M_Type
{
    ONCREATED,ONDESTORY
}