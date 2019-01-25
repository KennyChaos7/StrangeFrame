package org.k.SBase.Tools;

import android.util.Log;

import org.k.SBase.S;

/**
 * Created by Kenny on 18-12-27.
 */
public final class LogTool {
    private static final String TAG = "LogTool";

    private static String getTAG() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    public static void d(String content) {
        d(getTAG().equals("") ? TAG : getTAG(), content);
    }

    public static void d(String tag, String content) {
        Log.d(tag, content);
    }

    public static void e(String content) {
        e(getTAG().equals("") ? TAG : getTAG(), content);
    }

    public static void e(String tag, String content) {
        Log.e(tag, content);
    }

    public static void ee(String tag,String content){
        if (S.isDebug){
            Log.e(tag, content);
        }
    }

    public static void i(String content) {
        i(getTAG().equals("") ? TAG : getTAG(), content);
    }

    public static void i(String tag, String content) {
        Log.i(tag, content);
    }
}
