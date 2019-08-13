package com.reactnativenavigation.screens;

import android.app.Activity;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.views.LeftButtonOnClickListener;

class ScreenFactory {
    static Screen create(Activity activity,
                         ScreenParams screenParams,
                         LeftButtonOnClickListener leftButtonOnClickListener) {
        if (screenParams.isFragmentScreen()) {
            return new FragmentScreen(activity, screenParams, leftButtonOnClickListener);
        } else if (screenParams.hasTopTabs()) {
            if (screenParams.hasCollapsingTopBar()) {
                return new CollapsingViewPagerScreen(activity, screenParams, leftButtonOnClickListener);
            } else {
                return new ViewPagerScreen(activity, screenParams, leftButtonOnClickListener);
            }
        } else if (screenParams.hasCollapsingTopBar()) {
            return new CollapsingSingleScreen(activity, screenParams, leftButtonOnClickListener);
        } else {
            return new SingleScreen(activity, screenParams, leftButtonOnClickListener);
        }
    }
}
