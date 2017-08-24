package com.reactnativenavigation.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.Window;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class ImmersiveModeUtils {

    /**
     * This snippet shows the system bars. It does this by removing all the flags
     * except for the ones that make the content appear under the system bars.
     */
    public static void showSystemUI(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * Set the IMMERSIVE flag.
     * Set the content to appear under the system bars so that the content
     * doesn't resize when the system bars hide and show.
     */
    public static void hideSystemUI(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
