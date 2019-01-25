package org.k.SBase;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;

import org.k.SBase.Annotation.Task;
import org.k.SBase.Listener.BaseListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kenny on 18-11-8.
 */
final class STaskManager {
    private final String TAG = getClass().getSimpleName();
    private final static ExecutorService DEFAULT_THREAD = Executors.newCachedThreadPool();
    private BlockQueue<Runnable> runnable_queue = new BlockQueue<>();
    private BlockQueue<HashMap<Object,HashSet<Object>>> register_queue = new BlockQueue<>();
    private HandlerThread check_runnable_queue_thread = null;
    private Handler handler = null;

    protected void post(@NonNull Task.TYPE type,@NonNull Runnable runnable) {
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
    
    protected void start() {
        check_runnable_queue_thread = new HandlerThread("STaskManager");
        check_runnable_queue_thread.start();
        handler = new Handler(check_runnable_queue_thread.getLooper());
        handler.post(()->{
            while (!check_runnable_queue_thread.isInterrupted()) {
                try {
                    if (!runnable_queue.isEmpty()) {
                        DEFAULT_THREAD.execute(Objects.requireNonNull(runnable_queue.poll()));
                    }
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void end() {
        try {
            if (!DEFAULT_THREAD.awaitTermination(33,TimeUnit.MILLISECONDS))
                DEFAULT_THREAD.shutdown();
            runnable_queue.clear();
            runnable_queue = null;
            handler.post(null);
            handler = null;
            if (!check_runnable_queue_thread.quitSafely())
                check_runnable_queue_thread.quit();
            check_runnable_queue_thread = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void addRegister(@NonNull HashMap<Object,HashSet<Object>> unregister)
    {
        if (!register_queue.contains(unregister)) {
            register_queue.put(unregister);
        }
    }

    protected void sendTo(@NonNull Object topic,@NonNull Object obj)
    {
        if (topic == null || register_queue.isEmpty())
            throw new NullPointerException("don't had any topic register");
        for (int i = 0; i < register_queue.getSize() && register_queue.get(i) != null; i++){
            if (register_queue.get(i).value.get(topic) != null){
                HashSet<Object> targetList = register_queue.get(i).value.get(topic);
                Iterator iterator = targetList.iterator();
                while (iterator.hasNext()){
                    BaseListener listener = ((BaseListener)iterator.next());
                    listener.onListen(topic,obj);
                }
            }
        }
    }

}
