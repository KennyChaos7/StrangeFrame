package org.k.SBase;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import org.k.SBase.Annotation.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kenny on 18-11-8.
 */
final class STaskManager {
    private final static ExecutorService DEFAULT_THREAD = Executors.newCachedThreadPool();
    private LinkedBlockingQueue<Runnable> runnable_queue = new LinkedBlockingQueue<>();
    private HandlerThread check_runuable_queue_thread = null;
    private Handler handler = null;

    protected void post(Task.TYPE type, Runnable runnable)
    {
        try {
            switch (type) {
                case BACKGROUND:
                    runnable_queue.put(runnable);
                    break;
                case MAIN:
                    Handler tmpHandler = new Handler(Looper.getMainLooper());
                    tmpHandler.post(runnable);
                    break;
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    protected void start()
    {
        check_runuable_queue_thread = new HandlerThread("STaskManager");
        check_runuable_queue_thread.start();
        handler = new Handler(check_runuable_queue_thread.getLooper());
        handler.post(()->{
            while (!check_runuable_queue_thread.isInterrupted()) {
                try {
                    if (!runnable_queue.isEmpty()) {
                        for (Runnable runnable : runnable_queue) {
                            DEFAULT_THREAD.execute(runnable);
                            runnable_queue.remove(runnable);
                        }
                    }
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void end()
    {
        try {
            if (!DEFAULT_THREAD.awaitTermination(33,TimeUnit.MILLISECONDS))
                DEFAULT_THREAD.shutdown();
            runnable_queue.clear();
            runnable_queue = null;
            handler.post(null);
            handler = null;
            if (!check_runuable_queue_thread.quitSafely())
                check_runuable_queue_thread.quit();
            check_runuable_queue_thread = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
