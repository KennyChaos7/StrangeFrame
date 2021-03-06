package org.k.SBase.Annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Kenny on 18-7-26.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {
    /**
     * @return 返回在app中该view的id
     */
    int id();

    /**
     * listener
     * @return
     */
    Class<?> clazz() default View.OnClickListener.class;


}
