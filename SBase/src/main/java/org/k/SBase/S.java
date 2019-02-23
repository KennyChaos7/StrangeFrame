package org.k.SBase;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.support.annotation.NonNull;

/**
 * Created by Kenny on 18-7-27.
 * 启动STaskManager
 */
//TODO 还需修改为MVVM或MVP模式
public final class S {
    /**
     * 注解管理类
     */
    private final static SFilter sFilter = SFilter.getInstance();
    /**
     * 线程管理类
     */
    private final static STaskManager sTaskManager = new STaskManager();

    public static boolean isDebug = false;

    static {
        sTaskManager.start();
    }

    @Override
    protected void finalize() throws Throwable {
        sTaskManager.end();
        super.finalize();
    }

    /**
     * 错误捕捉机制
     * @param application
     */
    public static void setIsCatchException(@NonNull Application application){
        SCat.getInstance(application,false,false);
    }

    /**
     *
     * @param application
     * @param isCatchAndClose
     */
    public static void setIsCatchException(@NonNull Application application, boolean isCatchAndClose,boolean isSaveErrorInfo){
        SCat.getInstance(application,isCatchAndClose,isSaveErrorInfo);
    }

    /**
     * 注解
     * @param activity
     */
    public static void IN(@NonNull Activity activity) {
        sFilter.inject(sTaskManager,activity, new SViewHolder(activity));
    }

    /**
     *
     * @param fragment
     */
    public static void IN(@NonNull Fragment fragment) {
        //TODO 注解fragment,待测
        sFilter.inject(sTaskManager,fragment,new SViewHolder(fragment));
    }

    /**
     * 发送事件
     * @param topic 事件主题
     * @param content 发送内容
     */
    public static void sendTo(@NonNull Object topic ,@NonNull Object content){
        sTaskManager.sendTo(topic, content);
    }
}
