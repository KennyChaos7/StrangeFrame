package org.k.SBase;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.V;
import org.k.SBase.Model.BaseViewHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Kenny on 18-7-26.
 */
final class SAnnotationReader {

    /**
     * 注解类
     */
    // TODO 还需智能化
    @Deprecated
    private final static String[] mAnnotationNames = new String[]
            {"Event", "View"};

    /**
     * 支持的注解事件Listener类型
     */
    // TODO 还需智能化
    @Deprecated
    private final static String[] mFinalListenerNames = new String[]
            {"OnClickListener"};

    private static HashMap<String, Object> mListenerInstanceHashMap = new HashMap<>();

    /**
     * 保存已经注解过的viewHolder
     * 利用hashCode进行对比区分是否是不同的activity或者fragment
     */
    private static HashMap<String, BaseViewHolder> mHolderHashMap = new HashMap<>();

    /**
     * 无需递归检测注解的类的列表
     */
    private static final HashSet<Class<?>> DONINJECT = new HashSet<>();

    static {
        DONINJECT.add(Object.class);
        DONINJECT.add(Activity.class);
        DONINJECT.add(android.app.Fragment.class);
        try {
            DONINJECT.add(Class.forName("android.support.v4.app.Fragment"));
            DONINJECT.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable ignored) {
        }
    }

    /**
     * 注解控件对象
     * 注解添加方法
     *
     * @param o
     * @param viewHolder
     */
    //TODO 保存viewHolder, 然后每次进行比对
    static void inject(final Object o, BaseViewHolder viewHolder) {

        try {
            if (o == null || DONINJECT.contains(o))
                return;
            String key = o.getClass().getSimpleName().toLowerCase();
            if (mHolderHashMap.get(key) == null)
                mHolderHashMap.put(o.getClass().getSimpleName().toLowerCase(), viewHolder);
            else
                return;
            /*
             * 递归将父类也进行注解
             */
            inject(o.getClass().getSuperclass(), viewHolder);

            if (viewHolder == null)
                return;

            /*
             * 处理注解
             */
            Class<?> clazz = o.getClass();

            /*
             * 注解V
             */
            Field[] fields = clazz.getDeclaredFields();
            if (fields == null || fields.length <= 0)
                return;
            for (Field field : fields) {
//                if (field.getType().isArray() || field.getType().isPrimitive())
//                    continue;
                V v = field.getAnnotation(V.class);
                if (v == null)
                    continue;
                else {
                    View mView = viewHolder.findViewById(v.value());
                    if (mView == null)
                        continue;
                    else {
                        field.setAccessible(true);
                        /*
                         * field.set(注解对象，注解所代理的对象控件)
                         */
                        field.set(o, mView);
                    }
                }
            } //注解V结束

            /*
             * 注解Event
             */
            for (Method method : clazz.getDeclaredMethods()) {

                Event event = method.getAnnotation(Event.class);
                if (event == null)
                    continue;
                else {
                    method.setAccessible(true);

                    View v = viewHolder.findViewById(event.id());
                    if (v == null)
                        return;

                    Class listenerClass = event.clazz();
                    String listenerName = "set" + listenerClass.getSimpleName();
                    Object listener = mListenerInstanceHashMap.get(listenerName);
                    EventInvocationHandler invocationHandler = null;
                    boolean isAddedMethod = false;
                    if (listener != null)
                    {
                        invocationHandler = (EventInvocationHandler) Proxy.getInvocationHandler(listener);
                        isAddedMethod = v.equals(invocationHandler.getContext());
                        if (!isAddedMethod)
                            invocationHandler.addMethod(o.getClass().getSimpleName(),method);
                    }
                    if (!isAddedMethod)
                    {
                        invocationHandler = new EventInvocationHandler(o);
                        invocationHandler.addMethod(o.getClass().getSimpleName(), method);
                        listener = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class<?>[]{listenerClass}, invocationHandler);
                        mListenerInstanceHashMap.put(listenerName,listener);

                    }
                    Log.i("aa" , o.getClass().getSimpleName());
                    Method setMethod = v.getClass().getMethod(listenerName, listenerClass);
                    setMethod.invoke(v, listener);


                } //注解Event结束
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /*
     * 删除注解
     */
    static void uninject(final Object o) {

    }

}
