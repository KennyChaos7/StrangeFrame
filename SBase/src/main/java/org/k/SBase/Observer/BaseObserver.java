package org.k.SBase.Observer;

/**
 * Created by Kenny on 18-8-1.
 */
public interface BaseObserver<T> {

    void onDataUpdate(T t);

    @Override
    int hashCode();
}
