package org.k.SBase.Model;

import android.app.Activity;
import android.view.View;

/**
 * Created by Kenny on 18-7-27.
 */
public class BaseViewHolder {
    private View mView = null;
    private Activity mActivity = null;

    public BaseViewHolder(View view)
    {
        mView = view;
    }

    public BaseViewHolder(Object o)
    {
        if (o instanceof Activity)
            mActivity = (Activity) o;
        else if (o instanceof View)
            mView = (View)o;
    }

    public View findViewById(int viewId)
    {
        if (mView != null) return mView.findViewById(viewId);
        if (mActivity != null) return mActivity.findViewById(viewId);
        return null;
    }

    @Override
    public int hashCode() {
        if (mView != null) return mView.getId();
        if (mActivity != null) return Integer.valueOf(mActivity.getClass().getSimpleName());
        return -1;
    }
}
