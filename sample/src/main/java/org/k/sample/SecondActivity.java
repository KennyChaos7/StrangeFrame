package org.k.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.Task;
import org.k.SBase.Annotation.V;
import org.k.SBase.S;
import org.k.SBase.Tools.LogTool;

/**
 * Created by Kenny on 18-7-30.
 */
public class SecondActivity extends AppCompatActivity {

    @V(value = R.id.btn_test_s_listener_s)
    Button mButtonForTestSListener;

    @Event(id = R.id.btn_test_s_listener_s)
    public void c(android.view.View view)
    {
        LogTool.i("c");
        mButtonForTestSListener.setText("c");
    }

    @V(value = R.id.btn_task)
    Button btn_task;

    @Event(id = R.id.btn_task)
    public void task(V v)
    {
        a();
    }

    @Task(type = Task.TYPE.BACKGROUND)
    public void a()
    {
        LogTool.e("aaaaaa");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        S.IN(this);
    }
}
