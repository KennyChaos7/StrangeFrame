package org.k.SBase.Observer;


import org.k.SBase.Listener.StrangeListener;
import org.k.SBase.S_STATE;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/*
 * Created by Kenny on 18-7-30.
 * 管理所有观察者Observer
 * 负责将所有的Activity和Fragment对应的观察者Observer
 */
public class SWatcher {
    private final String TAG = getClass().getSimpleName().toLowerCase();

    private List<StrangeListener> mListenerArrayList = Collections.synchronizedList(new Vector<StrangeListener>());

    public S_STATE registerListener(StrangeListener listener)
    {
        if (listener == null)
            return S_STATE.RegisterFailed;

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
        return S_STATE.RegisterFailed;
    }

    public S_STATE unregisterListener(StrangeListener listener)
    {
        return mListenerArrayList.remove(listener) ? S_STATE.UnregisterSuccess : S_STATE.RegisterFailed;
    }

    private synchronized void reflex(Object type,Object... arg)
    {

    }


    enum M_Type
    {
        ONCREATED,ONDESTORY
    }
}
