package org.k.strangeframe.Observer;

/**
 * Created by Kenny on 18-8-1.
 */
public interface StrangeObserver<T> {

    void onStateChanged(T t);
}
