package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.utils.ViewUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class StatusBarBackground extends View {
    @Nullable private TitleBarBackground background;
    private StyleParams.Color backgroundColor = new StyleParams.Color();
    private StyleParams.Color secondaryBackgroundColor = new StyleParams.Color();

    public StatusBarBackground(Context context) {
        super(context);
        setId(ViewUtils.generateViewId());
        setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, ViewUtils.getStatusBarHeight()));
    }

    public void setStyle(StyleParams.Color backgroundColor, StyleParams.Color secondaryBackgroundColor) {
        if (colorsChanged(backgroundColor, secondaryBackgroundColor)) {
            this.backgroundColor = backgroundColor;
            this.secondaryBackgroundColor = secondaryBackgroundColor;
            createBackground(backgroundColor, secondaryBackgroundColor);
        }
    }

    private boolean colorsChanged(StyleParams.Color backgroundColor, StyleParams.Color secondaryBackgroundColor) {
        return !this.backgroundColor.equals(backgroundColor) ||
               !this.secondaryBackgroundColor.equals(secondaryBackgroundColor);
    }

    private void createBackground(StyleParams.Color backgroundColor, StyleParams.Color secondaryBackgroundColor) {
        if (secondaryBackgroundColor.hasColor()) {
            ColorDrawable transparent = new ColorDrawable(backgroundColor.getColor(Color.TRANSPARENT));
            ColorDrawable solid = new ColorDrawable(secondaryBackgroundColor.getColor(Color.TRANSPARENT));
            background = new TitleBarBackground(transparent, solid);
            setBackground(background);
        } else {
            setBackgroundColor(backgroundColor.getColor(Color.TRANSPARENT));
        }
    }

    public void onScroll(int scrollY) {
        // TODO navBarColorAnimationOffset - TitleBar height - StatusBar height
        if (scrollY > 1000 - 224 - ViewUtils.getStatusBarHeight()) {
            if (background != null) background.showSolidBackground();
        } else {
            if (background != null) background.showTranslucentBackground();
        }
    }
}
