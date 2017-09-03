package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.utils.ViewUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class StatusBarBackground extends View {
    private StyleParams.Color backgroundColor = new StyleParams.Color();

    public StatusBarBackground(Context context, StyleParams styleParams) {
        super(context);
        setId(ViewUtils.generateViewId());
        setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, styleParams.drawScreenBellowStatusBar ? ViewUtils.getStatusBarHeight() : 1));
    }

    public void setStyle(StyleParams.Color backgroundColor) {
        if (colorsChanged(backgroundColor)) {
            this.backgroundColor = backgroundColor;
            createBackground(backgroundColor);
        }
    }

    private boolean colorsChanged(StyleParams.Color backgroundColor) {
        return !this.backgroundColor.equals(backgroundColor);
    }

    private void createBackground(StyleParams.Color backgroundColor) {
        setBackgroundColor(backgroundColor.getColor(Color.TRANSPARENT));
    }
}
