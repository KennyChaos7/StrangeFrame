package org.k.SBase.Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Kenny on 19-1-10.
 * 当使用了这个注解后, 该类将自动绑定事件回调监听{@link org.k.SBase.Listener.BaseListener}
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Register {

    /*
        订阅的主题
     */
    String[] Topic() default {"Main"};
}
