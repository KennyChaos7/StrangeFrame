package org.k.SBase.Tools;

import org.k.SBase.BuildConfig;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kenny on 18-12-27.
 * 还待使用{@link java.util.logging.Formatter}规范一下格式
 */
@Deprecated
public final class LogTool {

    private static String getTAG() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    private static Logger getLogger(){
        return Logger.getLogger(BuildConfig.APPLICATION_ID);
    }

    public static void d(String content) {
        getLogger().log(Level.CONFIG,getTAG() + " - " + content);
    }

    public static void e(String content) {
        getLogger().log(Level.WARNING,getTAG() + " - " + content);
    }

    public static void debug(String content){
        getLogger().log(Level.SEVERE,getTAG() + " - " + content);
    }

    public static void i(String content) {
        getLogger().log(Level.INFO,getTAG() + " - " + content);
    }

}
