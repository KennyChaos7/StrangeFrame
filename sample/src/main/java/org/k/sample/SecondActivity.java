package org.k.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.V;
import org.k.SBase.S;

/**
 * Created by Kenny on 18-7-30.
 */
public class SecondActivity extends AppCompatActivity {

    @V(value = R.id.btn_test_s_listener_s)
    Button mButtonForTestSListener;

    @Event(id = R.id.btn_test_s_listener_s)
    public void c(View view)
    {
        Log.i("--","c");
        mButtonForTestSListener.setText("c");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        S.IN(this);
    }
}
