package org.k.SBase;

import org.k.SBase.Tools.LogTool;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Kenny on 18-11-8.
 * 利用java的反射reflect包中Proxy去实例化一个listener对象并存储起来
 */
final class SInvocationHandler implements InvocationHandler {
    private WeakReference<Object> mContextWeakReference = null;
    private final HashMap<String, Method> mMethodHashMap = new HashMap<>(1);

    SInvocationHandler(Object context) {
        this.mContextWeakReference = new WeakReference<>(context);
    }

    void addMethod(String className, Method method)
    {
        mMethodHashMap.put(className,method);
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
            method = mMethodHashMap.get(context.getClass().getSimpleName());
            LogTool.i(context.getClass().getSimpleName());
            if (method != null)
                return method.invoke(context,args);
        }
        return null;
    }

    Object getContext() {
        return mContextWeakReference.get();
    }
}