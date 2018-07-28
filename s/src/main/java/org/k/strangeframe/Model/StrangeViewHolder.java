package org.k.strangeframe.Model;

import android.app.Activity;
import android.view.View;

/**
 * Created by Kenny on 18-7-27.
 */
public class StrangeViewHolder{
    private View mView = null;
    private Activity mActivity = null;

    public StrangeViewHolder(View view)
    {
        mView = view;
    }

    public StrangeViewHolder(Activity activity)
    {
        mActivity = activity;
    }

    public View findViewById(int viewId)
    {
        if (mView != null) return mView.findViewById(viewId);
        if (mActivity != null) return mActivity.findViewById(viewId);
        return null;
    }
}
