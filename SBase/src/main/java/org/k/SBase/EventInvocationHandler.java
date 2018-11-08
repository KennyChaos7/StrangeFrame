package org.k.SBase;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Kenny on 18-11-8.
 */

// TODO 利用java的反射reflect包中Proxy去实例化一个listener对象并存储起来
public class EventInvocationHandler implements InvocationHandler {
    private WeakReference<Object> mContextWeakReference = null;
//    private final HashMap<String, Method> mMethodHashMap = new HashMap<>(1);
    private Method mMethod = null;

    EventInvocationHandler(Object context) {
        this.mContextWeakReference = new WeakReference<>(context);
    }

    void addMethod(String methodName, Method method) {
//        mMethodHashMap.put(methodName, method);
        this.mMethod = method;
    }

    /**
     * @param proxy  需被动态代理的对象
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object context = mContextWeakReference.get();
        if (context != null) {
//            String methodName = method.getName();
//            method = mMethodHashMap.get(methodName);
//            if (method != null) {
//                return method.invoke(context, args);
//            }else
//                Log.e(getClass().getSimpleName(), "2");

            if (mMethod != null)
                return mMethod.invoke(context,args);
        }
        return null;
    }

    Object getContext() {
        return mContextWeakReference.get();
    }
}