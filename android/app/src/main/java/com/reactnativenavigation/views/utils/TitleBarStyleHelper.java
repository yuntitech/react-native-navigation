package com.reactnativenavigation.views.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.utils.ViewUtils;
import com.reactnativenavigation.views.TitleBar;
import com.reactnativenavigation.views.TitleBarBackground;

public class TitleBarStyleHelper {
    private StyleParams.Color backgroundColor = new StyleParams.Color();
    private StyleParams.Color secondaryBackgroundColor = new StyleParams.Color();

    public boolean colorsChanged(StyleParams.Color backgroundColor, StyleParams.Color secondaryBackgroundColor) {
        return !this.backgroundColor.equals(backgroundColor) ||
               !this.secondaryBackgroundColor.equals(secondaryBackgroundColor);
    }

    public TitleBarBackground setBackground(TitleBar titleBar, StyleParams params) {
        ColorDrawable expanded = new ColorDrawable(params.secondaryTopBarColor.getColor(Color.TRANSPARENT));
        ColorDrawable collapsed = new ColorDrawable(params.topBarColor.getColor());
        TitleBarBackground titleBarBackground = new TitleBarBackground(expanded, collapsed);
        titleBar.setBackground(titleBarBackground);
        return titleBarBackground;
    }

    public void onScroll(TitleBarBackground background, int scrollY) {
        if (scrollY > 1000 - 224 - ViewUtils.getStatusBarHeight()) {
            if (background != null) background.showSolidBackground();
        } else {
            if (background != null) background.showTranslucentBackground();
        }
    }
}
