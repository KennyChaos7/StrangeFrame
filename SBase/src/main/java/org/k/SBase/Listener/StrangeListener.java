package org.k.SBase.Listener;

/**
 * Created by Kenny on 18-8-1.
 */
public interface StrangeListener<T> {

    /**
     * 数据更新回调
     * @param callBack
     */
    void onListen(CallBack<T> callBack);

}
abstract class CallBack<T>
{
    abstract void onCallBack(T t);
}
