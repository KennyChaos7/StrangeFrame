package org.k.strangeframe;

import android.view.View;

import org.k.strangeframe.Annotation.Event;
import org.k.strangeframe.Model.StrangeViewHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Kenny on 18-7-26.
 */
public final class StrangeAnnotationReader {
    private final static String[] mAnnotationNames = new String[]
            {"Event","View","JOB"};

    static void addMethod(Class clazz, StrangeViewHolder viewHolder) {

        try {
            /**
             * 处理注解
             */
            for (Method method : clazz.getDeclaredMethods()) {
                /**
                 * 注解Event
                 */
                Event event = method.getAnnotation(Event.class);
                if (event == null || viewHolder == null)
                    continue;
                else {

                    Class<?> listenerClass = event.clazz();

                    // TODO 设置对应的listener事件名称
                    String listenerName = "";
                    // TODO 生成对应的listener事件的实例对象
                    Object listener = new Object();

                    View mView = viewHolder.findViewById(event.id());

                    Method viewHadMethod = mView.getClass().getMethod(listenerName, event.clazz());
                    viewHadMethod.invoke(mView, listener);
                } //注解结束
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
