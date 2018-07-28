package org.k.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.k.strangeframe.Annotation.Event;
import org.k.strangeframe.Annotation.V;
import org.k.strangeframe.s;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s.IN(this);

//        mButtonToTestSListener = findViewById(R.id.btn_test_s_listener);
    }

    @V(id = R.id.btn_test_s_listener)
    Button mButtonToTestSListener;

    @Event(id = R.id.btn_test_s_listener)
    public void c(View v)
    {
        Log.i("t","c");
    }
}
