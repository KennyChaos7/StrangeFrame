package org.k.SBase.Observer;

/**
 * Created by Kenny on 18-8-1.
 * 等转移为MMVM模式或者MVP模式才会启用
 */
@Deprecated
public interface BaseObserver<T> {

    void onDataUpdate(T t);

    @Override
    int hashCode();
}
