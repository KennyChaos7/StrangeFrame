package org.k.strangeframe;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import org.k.strangeframe.Annotation.Event;
import org.k.strangeframe.Model.StrangeViewHolder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by Kenny on 18-7-26.
 */
public final class StrangeAnnotationReader {

    /**
     * 注解类
     */
    // TODO 还需智能化
    private final static String[] mAnnotationNames = new String[]
            {"Event", "View"};

    /**
     * 支持的注解事件Listener类型
     */
    // TODO 还需智能化
    private final static String[] mFinalListenerNames = new String[]
            {"OnClickListener"};

    /**
     * 保存已经注解过的viewHolder
     * 利用hashCode进行对比区分是否是不同的activity或者fragment
     */
    private HashMap<String,StrangeViewHolder> mHolderHashMap = new HashMap<>();


    /**
     * 注解控件对象
     * @param o
     * @param viewHolder
     */
    static void injectView(final Object o, StrangeViewHolder viewHolder)
    {
        try{
            Class<?> clazz = o.getClass();

        }catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 注解添加方法
     *
     * @param o
     * @param viewHolder
     */
    static void addMethod(final Object o, StrangeViewHolder viewHolder) {

        try {
            /**
             * 处理注解
             */
            Class<?> clazz = o.getClass();
            for (Method method : clazz.getDeclaredMethods()) {
                /**
                 * 注解Event
                 */
                Event event = method.getAnnotation(Event.class);
                if (event == null || viewHolder == null)
                    continue;
                else {

                    /*
                    Class<?> listenerClass = event.clazz();

                    // TODO 设置对应的listener事件名称
                    String listenerName = "set" + listenerClass.getSimpleName();
                    // TODO 生成对应的listener事件的实例对象
                    Object listener = ?????
                    // TODO 利用java的反射reflect包中Proxy去实例化一个listener对象并存储起来


                    View mView = viewHolder.findViewById(event.id());

                    Method viewHadMethod = mView.getClass().getMethod(listenerName, event.clazz());
                    viewHadMethod.invoke(mView, listener);
                    */

                    @SuppressWarnings("粗制滥造版")
                    View mView = viewHolder.findViewById(event.id());
                    if (mView == null)
                        return;
                    mView.setOnClickListener(v -> {
                        try {
                            /**
                             * 根据method的参数要求
                             * invoke(注解对象，注解所代理的对象的入参)
                             * 这里对应的是setOnClickListener(View view),这里的view是指activity或fragment当前的view对象
                             */
                            method.invoke(o,((Activity)o).getCurrentFocus());
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    });

                } //注解结束
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // TODO 利用java的反射reflect包中Proxy去实例化一个listener对象并存储起来
    class HolderListenerHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }
}