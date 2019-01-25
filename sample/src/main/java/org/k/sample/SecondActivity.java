package org.k.sample;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.Register;
import org.k.SBase.Annotation.Task;
import org.k.SBase.Annotation.V;
import org.k.SBase.Listener.BaseListener;
import org.k.SBase.S;
import org.k.SBase.Tools.LogTool;

/**
 * Created by Kenny on 18-7-30.
 */
@Register()
public class SecondActivity extends AppCompatActivity implements BaseListener<String,Object> {
    private S s = null;

    @V(value = R.id.btn_test_s_listener_s)
    Button mButtonForTestSListener;

    @Event(id = R.id.btn_test_s_listener_s)
    public void c(android.view.View view) {
        LogTool.i(getClass().getSimpleName(),"R.id.btn_test_s_listener_s");
        mButtonForTestSListener.setText("c");
    }

    @V(value = R.id.btn_send_to)
    Button mButtonSendTo;

    @Event(id = R.id.btn_send_to)
    public void sendto(View v)
    {
        s.sendTo("Main",SystemClock.currentThreadTimeMillis());
    }

    @Task(type = Task.TYPE.BACKGROUND)
    public void a() {
        LogTool.e(getClass().getSimpleName(),"Task.TYPE.BACKGROUND");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        S.IN(this);
    }

    @Override
    public void onListen(String topic, Object obj) {
        LogTool.e(getClass().getSimpleName(),String.valueOf(obj));
    }
}
