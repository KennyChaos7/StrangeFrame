package org.k.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.V;
import org.k.SBase.S;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        S.IN(this);

//        mButtonToTestSListener = findViewById(R.id.btn_test_s_listener);
    }

    @V(value = R.id.btn_test_s_listener)
    public Button mButtonToTestSListener;

    @Event(id = R.id.btn_test_s_listener)
    public void c(View v)
    {
        Log.i("t","c " + mButtonToTestSListener.getAccessibilityClassName());
        startActivity(new Intent(this,SecondActivity.class));
    }
}
