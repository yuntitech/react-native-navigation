package com.reactnativenavigation.react.coordinator;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class CoordinatorLayoutManager extends ViewGroupManager<CoordinatorLayoutView> {

	private final static String CLASS_NAME = "RNNCoordinatorLayout";

	@Override
	public String getName() {
		return CLASS_NAME;
	}

	@Override
	protected CoordinatorLayoutView createViewInstance(ThemedReactContext reactContext) {
		return new CoordinatorLayoutView(reactContext);
	}

	@Override
	public void addView(CoordinatorLayoutView parent, View child, int index) {
		super.addView(parent, child, index);
		if (child instanceof NestedScrollView) {
			CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.MATCH_PARENT
			);

			params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
			child.setLayoutParams(params);
		}
	}
}
