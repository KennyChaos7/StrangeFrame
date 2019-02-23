package org.k.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import org.k.SBase.Annotation.Event;
import org.k.SBase.Annotation.Register;
import org.k.SBase.Annotation.V;
import org.k.SBase.S;

/**
 * Created by Kenny on 19-2-21.
 */
@Register(Topic = {"Fragment"})
public class MFragmentActivity extends FragmentActivity {
    private FirstFragment firstFragment = new FirstFragment();
    private SecondFragment secondFragment = new SecondFragment();
    private FragmentTransaction transaction = null;

    @V(R.id.btn_switch_fragment)
    private Button btn_switch_fragment = null;
    @Event(id = R.id.btn_switch_fragment)
    public void switch_fragment(View view){
//        transaction = getSupportFragmentManager().beginTransaction();
//        if (!secondFragment.isAdded()){
////            transaction.hide(firstFragment);
//            transaction.replace(R.id.fragment,secondFragment);
//        } else if (firstFragment.isHidden()){
//            transaction.hide(secondFragment);
//            transaction.show(firstFragment);
//        } else if (secondFragment.isHidden()){
//            transaction.hide(firstFragment);
//            transaction.show(secondFragment);
//        }
//        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle saveInstanceBundle) {
        super.onCreate(saveInstanceBundle);
        setContentView(R.layout.activity_mfragment);
        S.IN(this);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment,firstFragment);
        transaction.commit();
    }
}
