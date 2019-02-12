package org.k.SBase;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import org.k.SBase.Tools.LogTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenny on 19-1-26.
 * 错误捕捉机制
 * 实现{@link Thread.UncaughtExceptionHandler}同时捕获当前app的版本,android版本以及手机型号之类的信息
 * 然后通过{@link #collectInfo(Throwable)}保存在sd卡或手机存储中
 * 将用时间戳作为错误信息文件的保存文件名
 * 最后通过设置{@link #isCatchAndClose}来决定是否捕捉到错误关闭app
 */
public class SCat implements Thread.UncaughtExceptionHandler, Application.ActivityLifecycleCallbacks{
    private final String TAG = getClass().getSimpleName();
    private boolean isCatchAndClose = false;
    private boolean isSaveErrorInfo = true;
    private Application application;
    private Thread.UncaughtExceptionHandler defaultCatchHandler = null;
    private List<Activity> activityList = new ArrayList<>();

    private static SCat sCat = null;
    public static void getInstance(@NonNull Application application, boolean isCatchAndClose,boolean isSaveErrorInfo){
        synchronized (SCat.class){
            if (sCat == null){
                sCat = new SCat(application,isCatchAndClose,isSaveErrorInfo);
            }
        }
    }
    private SCat(Application application,boolean isCatchAndClose,boolean isSaveErrorInfo){
        this.application = application;
        this.isCatchAndClose = isCatchAndClose;
        this.isSaveErrorInfo = isSaveErrorInfo;
        defaultCatchHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //TODO 捕捉错误
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogTool.ee(TAG,String.valueOf(checkPermission()));
        if (defaultCatchHandler != null && e != null)
            defaultCatchHandler.uncaughtException(t,e);
        else
            SCatch(t,e);
    }

    private void SCatch(Thread t, Throwable e) {
        if (t == null)
            return;
        if (e == null)
            return ;
        if (checkPermission() && isSaveErrorInfo)
            collectInfo(e);
        closeAPP(isCatchAndClose);
    }

    private void collectInfo(Throwable e) {
        //TODO 将错误信息保存在sd中,并且以时间戳和
        if (application == null)
            return;
        /*
            检查sd是否可用
            还需检查是否有权限
         */
        try {
            File error_info = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                error_info = new File(Environment.getExternalStorageDirectory() + "/" + BuildConfig.APPLICATION_ID + SystemClock.currentThreadTimeMillis());
            } else
                error_info = new File(application.getCacheDir() + "/" + BuildConfig.APPLICATION_ID + SystemClock.currentThreadTimeMillis());
            OutputStream opStream = new FileOutputStream(error_info);
            StringBuilder info = new StringBuilder();
            info.append("Exception: ").append(e.toString()).append("\n");
            info.append("PackageName: ").append(application.getApplicationInfo().packageName).append("\n");
            info.append("Name: ").append(application.getApplicationInfo().name).append("\n");
            info.append("Permission: ").append(application.getApplicationInfo().permission).append("\n");
            opStream.write(info.toString().getBytes());
            opStream.flush();
            opStream.close();
            opStream = null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void closeAPP(boolean isCatchAndClose){
        if (isCatchAndClose){
            final List<Activity> finalActivityList= activityList;
            synchronized (finalActivityList) {
//            ActivityManager activityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
//            if (activityManager != null){
//                activityManager.getAppTasks().get(0).
//            }
                Activity activity = activityList.get(activityList.size() - 1);
                activity.finish();
                activityList.remove(activity);
            }
        }
    }

    private boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PackageManager.PERMISSION_GRANTED == application.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && PackageManager.PERMISSION_GRANTED == application.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }else {
            PackageManager packageManager = application.getPackageManager();
            return packageManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,application.getPackageName()) == PackageManager.PERMISSION_GRANTED
                    && packageManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,application.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (!activityList.contains(activity))
            activityList.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityList.remove(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityList.remove(activity);
    }
}
