package org.k.strangeframe.Model;

import org.k.strangeframe.Observer.StrangeObserver;
import org.k.strangeframe.S_STATE;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/*
 * Created by Kenny on 18-8-1.
 * 所有实现了这个类的Activity或Fragment对象的生命周期都应该会传递到这个Model类里
 */
public class SObsModel<T> implements StrangeObserver<T> {
    private S_STATE mCurrentState = S_STATE.HadRegistered;

    public SObsModel(Class clazz,T t)
    {

    }

    @Override
    public void onStateChanged(T t) {

    }
}
