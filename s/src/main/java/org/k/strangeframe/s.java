package org.k.strangeframe;

import android.app.Activity;

import org.k.strangeframe.Model.StrangeViewHolder;

/**
 * Created by Kenny on 18-7-27.
 */
public final class s {

    /**
     * s框架统一对外接口
     * @param activity
     */
    public static void IN(Activity activity)
    {
        _in(activity);
    }

    /**
     *
     * @param activity
     */
    private static void _in(Activity activity)
    {
        StrangeAnnotationReader.injectView(activity,new StrangeViewHolder(activity));
        StrangeAnnotationReader.addMethod(activity,new StrangeViewHolder(activity));
    }
}
