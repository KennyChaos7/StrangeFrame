package org.k.SBase;

import org.k.SBase.Observer.BaseObserver;

/*
 * Created by Kenny on 18-8-1.
 * 所有实现了这个类的Activity或Fragment对象的生命周期都应该会传递到这个Model类里
 */
@Deprecated
public class SObsModel<T> implements BaseObserver<T> {


    public SObsModel(Class clazz,T t)
    {

    }

    @Override
    public void onDataUpdate(T t) {

    }
}