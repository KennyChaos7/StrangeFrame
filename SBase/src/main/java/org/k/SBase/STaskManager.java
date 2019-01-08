package org.k.SBase;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import org.k.SBase.Tools.LogTool;

/**
 * Created by Kenny on 18-11-8.
 */
public class STaskManager {
    private Context mContext = null;
    private HandlerThread handlerThread = new HandlerThread(this.getClass().getName());
    private Handler handler = null;

    public STaskManager()
    {
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper(), msg -> {
            LogTool.d(String.valueOf(msg.obj));
            return true;
        });
    }

    public STaskManager(Context context)
    {
        this();
        this.mContext = context;
    }

//    public void runInBackground(Object o)
//    {
//        Message message = handler.obtainMessage();
//        message.obj = o;
//        handler.sendMessage(message);
//    }
    public void runInBackground(Runnable runnable)
    {
        handler.post(runnable);
    }
}
