package org.k.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Button;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.Register;
import org.k.SBase.Annotation.Task;
import org.k.SBase.Annotation.V;
import org.k.SBase.Listener.BaseListener;
import org.k.SBase.S;
import org.k.SBase.Tools.LogTool;

@Register
public class MainActivity extends AppCompatActivity implements BaseListener<String,Object> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        S.isDebug = true;
        S.IN(this);

    }

    @V(value = R.id.btn_test_s_listener)
    public Button mButtonToTestSListener;

    @Event(id = R.id.btn_test_s_listener,clazz = android.view.View.OnTouchListener.class)
    public boolean c(android.view.View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startActivity(new Intent(this, SecondActivity.class));
        }
        return true;
    }

    @Task(type = Task.TYPE.MAIN)
    public void m()
    {
        LogTool.e(String.valueOf(SystemClock.currentThreadTimeMillis()));
    }

    @Task(type = Task.TYPE.BACKGROUND)
    public void b()
    {
        LogTool.e(String.valueOf(SystemClock.currentThreadTimeMillis()));
    }

    @Override
    public void onListen(String topic, Object obj) {
        LogTool.e(getClass().getSimpleName(),String.valueOf(obj));
    }
}
