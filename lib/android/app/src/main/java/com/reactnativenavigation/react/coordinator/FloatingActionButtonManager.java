package com.reactnativenavigation.react.coordinator;


import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class FloatingActionButtonManager extends SimpleViewManager<FloatingActionButtonView> {

	private final static String CLASS_NAME = "RNNFloatingActionButton";

	@Override
	public String getName() {
		return CLASS_NAME;
	}

	@Override
	protected FloatingActionButtonView createViewInstance(ThemedReactContext reactContext) {
		return new FloatingActionButtonView(reactContext);
	}

	@ReactProp(name = "icon")
	public void setIcon(FloatingActionButtonView fab, String icon) {
		//TODO: use ImageLoader - not here yet
	}

	@ReactProp(name = "anchor")
	public void setAnchor(FloatingActionButtonView fab, int viewId) {
		//DO something ¯\_(ツ)_/¯
	}

	@ReactProp(name = "gravityTop")
	public void setGravityTop(FloatingActionButtonView fab, boolean gravityTop) {
		if (gravityTop) {
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
			if ((params.gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
				params.gravity = params.gravity & ~Gravity.BOTTOM;
			}
			params.gravity = params.gravity | Gravity.TOP;
			fab.setLayoutParams(params);
		}
	}

	@ReactProp(name = "gravityBottom")
	public void setGravityBottom(FloatingActionButtonView fab, boolean gravityBottom) {
		if (gravityBottom) {
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
			if ((params.gravity & Gravity.TOP) == Gravity.TOP) {
				params.gravity = params.gravity & ~Gravity.TOP;
			}
			params.gravity = params.gravity | Gravity.BOTTOM;
			fab.setLayoutParams(params);
		}
	}

	@ReactProp(name = "gravityRight")
	public void setGravityRight(final FloatingActionButtonView fab, boolean gravityRight) {
		if (gravityRight) {
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
			if ((params.gravity & Gravity.LEFT) == Gravity.LEFT) {
				params.gravity = params.gravity & ~Gravity.LEFT;
			}
			params.gravity = params.gravity | Gravity.RIGHT;
			fab.setLayoutParams(params);
		}
	}

	@ReactProp(name = "gravityLeft")
	public void setGravityLeft(final FloatingActionButtonView fab, boolean gravityLeft) {
		if (gravityLeft) {
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
			if ((params.gravity & Gravity.RIGHT) == Gravity.RIGHT) {
				params.gravity = params.gravity & ~Gravity.RIGHT;
			}
			params.gravity = params.gravity | Gravity.LEFT;
			fab.setLayoutParams(params);
		}
	}
}
