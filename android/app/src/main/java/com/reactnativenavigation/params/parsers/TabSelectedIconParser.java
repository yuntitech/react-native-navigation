package com.reactnativenavigation.params.parsers;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.reactnativenavigation.react.ImageLoader;

class TabSelectedIconParser extends Parser {

    private Bundle params;

    TabSelectedIconParser(Bundle params) {
        this.params = params;
    }

    public Drawable parse() {
        Drawable tabIcon = null;
        if (hasKey(params, "selectedIcon")) {
            tabIcon = ImageLoader.loadImage(params.getString("selectedIcon"));
        }
        return tabIcon;
    }
}
