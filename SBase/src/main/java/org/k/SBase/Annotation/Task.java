package org.k.SBase.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Kenny on 18-11-8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Task {

    /*
        优先度设置
        0～1依序增加优先度
     */
    int priority() default 0;

    /*
        线程模式
     */
    TYPE type() default TYPE.FRONT;

    /*
        延迟时间
     */
    long delayTime() default 0;

    public enum TYPE{
        BACKGROUND,FRONT
    }
}
