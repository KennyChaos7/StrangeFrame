package org.k.SBase;

import android.app.Activity;
import android.view.View;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.Task;
import org.k.SBase.Annotation.V;
import org.k.SBase.Model.BaseViewHolder;
import org.k.SBase.Tools.LogTool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Kenny on 18-7-26.
 */
final class SFilter {

    /**
     * 注解类
     */
    // TODO 还需智能化
    @Deprecated
    private final static String[] mAnnotationNames = new String[]{"Event", "V","Task"};

    /**
     * 支持的注解事件Listener类型
     */
    // TODO 还需智能化
    @Deprecated
    private final static String[] mFinalListenerNames = new String[]{"OnClickListener"};

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

    /**
     * 线程管理类
     */
    private final static STaskManager sTaskManager = new STaskManager();

    static {
        sTaskManager.start();
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
     * 保存viewHolder, 然后每次进行比对
     * 注解控件对象
     * 注解添加方法
     * @param o
     * @param viewHolder
     */
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
             * 双亲委托模型
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
                V v = field.getAnnotation(V.class);
                if (v == null)
                    continue;
                else {
                    android.view.View mView = viewHolder.findViewById(v.value());
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
                Task task = method.getAnnotation(Task.class);
                if (event != null){
                    method.setAccessible(true);
                    View view = viewHolder.findViewById(event.id());
                    if (view == null)
                        return;
                    Class listenerClass = event.clazz();
                    String listenerName = "set" + listenerClass.getSimpleName();
                    Object listener = mListenerInstanceHashMap.get(listenerName);
                    SInvocationHandler invocationHandler = null;
                    boolean isAddedMethod = false;
                    if (listener != null)
                    {
                        invocationHandler = (SInvocationHandler) Proxy.getInvocationHandler(listener);
                        isAddedMethod = view.equals(invocationHandler.getContext());
                        if (!isAddedMethod)
                            invocationHandler.addMethod(o.getClass().getSimpleName(),method);
                    }
                    if (!isAddedMethod)
                    {
                        invocationHandler = new SInvocationHandler(o);
                        invocationHandler.addMethod(o.getClass().getSimpleName(), method);
                        listener = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class<?>[]{listenerClass}, invocationHandler);
                        mListenerInstanceHashMap.put(listenerName,listener);

                    }
                    LogTool.i(o.getClass().getSimpleName());
                    Method setMethod = view.getClass().getMethod(listenerName, listenerClass);
                    setMethod.invoke(view, listener);
                } //注解Event结束

                /*
                 *   注解Task
                 */
                else if (task != null)
                {
                    method.setAccessible(true);
                    SInvocationHandler invocationHandler = new SInvocationHandler(o);
                    invocationHandler.addMethod(o.getClass().getSimpleName(),method);
                    Object r = Proxy.newProxyInstance(Runnable.class.getClassLoader(),new Class<?>[]{Runnable.class},invocationHandler);
                    Task.TYPE type = task.type();
                    sTaskManager.post(type,(Runnable) r);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /*
     * 删除注解
     */
    static void uninject(final Object o) {
        sTaskManager.end();
    }
}
