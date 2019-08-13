package com.reactnativenavigation.layouts;

import android.app.Activity;
import android.widget.RelativeLayout;

public abstract class BaseLayout extends RelativeLayout implements Layout {

    public BaseLayout(Activity activity) {
        super(activity);
    }

    protected Activity getActivity() {
        return (Activity) getContext();
    }
}
