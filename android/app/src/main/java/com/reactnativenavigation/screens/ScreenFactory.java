package com.reactnativenavigation.screens;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.views.LeftButtonOnClickListener;

import androidx.appcompat.app.AppCompatActivity;

class ScreenFactory {
    static Screen create(AppCompatActivity activity,
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
