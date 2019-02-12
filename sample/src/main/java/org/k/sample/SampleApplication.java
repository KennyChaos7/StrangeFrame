package org.k.sample;

import android.app.Application;

import org.k.SBase.S;

/**
 * Created by Kenny on 19-1-27.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        S.isDebug = true;
        S.setIsCatchException(this,true,true);

    }
}
