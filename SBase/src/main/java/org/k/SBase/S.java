package org.k.SBase;

import android.app.Activity;

import org.k.SBase.Model.BaseViewHolder;

import java.util.HashSet;

/**
 * Created by Kenny on 18-7-27.
 * 启动STaskManager
 */
public class S {
    /**
     * 保存对于已经注册消息通知监听的activity列表
     */
    private HashSet<String> mHadRegisterActivitiesNames = new HashSet<>();

    /**
     * s框架统一对外接口
     * @param activity
     */
    public static void IN(Activity activity)
    {
        if (activity != null) {
            SFilter.inject(activity, new BaseViewHolder(activity));
        }
    }


}
