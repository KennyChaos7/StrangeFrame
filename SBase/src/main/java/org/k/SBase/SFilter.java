package org.k.SBase;

import android.app.Activity;
import android.view.View;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.Register;
import org.k.SBase.Annotation.Task;
import org.k.SBase.Annotation.V;
import org.k.SBase.Listener.BaseListener;
import org.k.SBase.Tools.LogTool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Kenny on 18-7-26.
 */
//TODO 还需要处理Fragment和Activity的不同
final class SFilter {
    private final String TAG = getClass().getSimpleName().toLowerCase();
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

    private HashMap<String, Object> mListenerInstanceHashMap = new HashMap<>();

    /**
     * 保存已经注解过的viewHolder
     * 利用hashCode进行对比区分是否是不同的activity或者fragment
     */
    private HashMap<String, BaseViewHolder> mHolderHashMap = new HashMap<>();

    /**
     * 保存已经注解过的activity/fragment
     * 以主题为key
     */
    private HashMap<Object,HashSet<Object>> mRegisterTopicHashMap = new HashMap<>();

    /**
     * 无需递归检测注解的类的列表
     */
    private static final HashSet<Class<?>> DONINJECT = new HashSet<>();


    /**
     *  单例模式
     */
    private static SFilter instance = null;
    protected static SFilter getInstance(){
        synchronized (SFilter.class){
            if (instance == null)
                instance = new SFilter();
            return instance;
        }
    }

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
     * 保存viewHolder, 然后每次进行比对
     * 注解控件对象
     * 注解添加方法
     * @param o Activity 或 Fragment
     * @param viewHolder
     */
     void inject(final STaskManager sTaskManager,final Object o, BaseViewHolder viewHolder) {

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
            inject(sTaskManager,o.getClass().getSuperclass(), viewHolder);
            inject_view(viewHolder,o);
            inject_event(viewHolder,o);
            inject_task(sTaskManager,o);
            inject_register(sTaskManager,o);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 注解Event
     * @param viewHolder
     * @param targetObject  Activity 或 Fragment
     */
    private void inject_view(BaseViewHolder viewHolder,Object targetObject) throws IllegalAccessException {
        if (viewHolder == null)
            return;
        Class<?> clazz = targetObject.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length <= 0)
            return;
        for (Field field : fields) {
            V v = field.getAnnotation(V.class);
            if (v == null)
                return;
            else {
                android.view.View mView = viewHolder.findViewById(v.value());
                if (mView == null)
                    return;
                else {
                    field.setAccessible(true);
                    field.set(targetObject, mView);
                }
            }
        }
    }

    /**
     * 注解Event
     * @
     * @param viewHolder
     * @param targetObject  Activity 或 Fragment
     */
    private void inject_event(BaseViewHolder viewHolder,Object targetObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = targetObject.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            Event event = method.getAnnotation(Event.class);
            if (event != null) {
                method.setAccessible(true);
                View view = viewHolder.findViewById(event.id());
                if (view == null)
                    return;
                Class listenerClass = event.clazz();
                String listenerName = "set" + listenerClass.getSimpleName();
                Object listener = mListenerInstanceHashMap.get(listenerName);
                SInvocationHandler invocationHandler = null;
                boolean isAddedMethod = false;
                if (listener != null) {
                    invocationHandler = (SInvocationHandler) Proxy.getInvocationHandler(listener);
                    isAddedMethod = view.equals(invocationHandler.getContext());
                    if (!isAddedMethod)
                        invocationHandler.addMethod(targetObject.getClass().getSimpleName(), method);
                }
                if (!isAddedMethod) {
                    invocationHandler = new SInvocationHandler(targetObject);
                    invocationHandler.addMethod(targetObject.getClass().getSimpleName(), method);
                    listener = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class<?>[]{listenerClass}, invocationHandler);
                    mListenerInstanceHashMap.put(listenerName, listener);
                }
                Method setMethod = view.getClass().getMethod(listenerName, listenerClass);
                setMethod.invoke(view, listener);
            }
        }
    }

    /**
     * 注解Task
     * @param sTaskManager
     * @param targetObject  Activity 或 Fragment
     */
    private void inject_task(STaskManager sTaskManager,Object targetObject) {
        Class<?> clazz = targetObject.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            Task task = method.getAnnotation(Task.class);
            if (task != null)
            {
                method.setAccessible(true);
                SInvocationHandler invocationHandler = new SInvocationHandler(targetObject);
                invocationHandler.addMethod(targetObject.getClass().getSimpleName(),method);
                Object r = Proxy.newProxyInstance(Runnable.class.getClassLoader(),new Class<?>[]{Runnable.class},invocationHandler);
                Task.TYPE type = task.type();
                sTaskManager.post(type,(Runnable) r);
            }
        }
    }

    /**
     * 注解Register
     * 先校验注解是否实现了 {@link org.k.SBase.Listener.BaseListener}
     * 然后再判断是否已经注解过了 {@link #mRegisterTopicHashMap}
     * 再将注解传入到 {@link STaskManager#addRegister(HashMap)}中
     * @param sTaskManager
     * @param targetObject  Activity 或 Fragment
     */
    private void inject_register(STaskManager sTaskManager,Object targetObject) {
        Class clazz = targetObject.getClass();
        Register register = (Register) clazz.getAnnotation(Register.class);
        Class[] interfaces = clazz.getInterfaces();
        if (interfaces.length == 0)
            return;
        if (register == null)
            return;
        //TODO 优化循环
        for (Class anInterface : interfaces) {
            if (anInterface == BaseListener.class) {
                LogTool.ee(TAG, Arrays.toString(register.Topic()));
                for (String topic : register.Topic()) {
                    /*
                        校验是否在mRegisterTopicHashMap中
                      */
                    if (mRegisterTopicHashMap.get(topic) == null) {
                        HashSet<Object> tmp = new HashSet<>();
                        tmp.add(targetObject);
                        mRegisterTopicHashMap.put(topic,tmp);
                        tmp = null;
                        return;
                    }else if (mRegisterTopicHashMap.get(topic).contains(targetObject))
                        return;
                    /*
                        如果没有在mRegisterTopic中,则添加到里面
                      */
                    else {
                        mRegisterTopicHashMap.get(topic).add(targetObject);
                    }
                }
                sTaskManager.addRegister(mRegisterTopicHashMap);
            }
        }
    }
}
