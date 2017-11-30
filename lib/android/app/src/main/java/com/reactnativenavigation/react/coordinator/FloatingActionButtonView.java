package com.reactnativenavigation.react.coordinator;


import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.reactnativenavigation.utils.UiUtils;

public class FloatingActionButtonView extends FloatingActionButton {

	private final static int FAB_MARGIN = 16;

	public FloatingActionButtonView(Context context) {
		super(context);

		CoordinatorLayout.LayoutParams params =
				new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
		params.bottomMargin = UiUtils.dpToPx(getContext(), FAB_MARGIN);
		params.topMargin = UiUtils.dpToPx(getContext(), FAB_MARGIN);
		params.rightMargin = UiUtils.dpToPx(getContext(), FAB_MARGIN);
		params.leftMargin = UiUtils.dpToPx(getContext(), FAB_MARGIN);
		this.setLayoutParams(params);

		setClickable(true);
	}

	public void setIcon(String path) {
		//TODO: implement after buttons
	}

	public void setBackground(int color) {
		setBackgroundTintList(ColorStateList.valueOf(color));
	}

	public void setHidden(boolean hidden) {
		if (hidden) {
			hide();
		} else {
			show();
		}
	}

	public void setFabElevation(float elevation) {
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			setElevation(elevation);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean result = super.onTouchEvent(ev);
		if (!result) {
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				cancelLongPress();
			}
			setPressed(false);
		}
		return result;
	}

	public void setGravityLeft(boolean enabled) {
		if (enabled) {
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) getLayoutParams();
			removeGravityParam(params, Gravity.RIGHT);
			setGravityParam(params, Gravity.LEFT);
			setLayoutParams(params);
		}
	}

	public void setGravityRight(boolean enabled) {
		if (enabled) {
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) getLayoutParams();
			removeGravityParam(params, Gravity.LEFT);
			setGravityParam(params, Gravity.RIGHT);
			setLayoutParams(params);
		}
	}

	public void setGravityBottom(boolean enabled) {
		if (enabled) {
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) getLayoutParams();
			removeGravityParam(params, Gravity.TOP);
			setGravityParam(params, Gravity.BOTTOM);
			setLayoutParams(params);
		}
	}

	public void setGravityTop(boolean enabled) {
		if (enabled) {
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) getLayoutParams();
			removeGravityParam(params, Gravity.BOTTOM);
			setGravityParam(params, Gravity.TOP);
			setLayoutParams(params);
		}
	}

	private void setGravityParam(CoordinatorLayout.LayoutParams params, int gravityParam) {
		params.gravity = params.gravity | gravityParam;
	}

	private void removeGravityParam(CoordinatorLayout.LayoutParams params, int gravityParam) {
		if ((params.gravity & gravityParam) == gravityParam) {
			params.gravity = params.gravity & ~gravityParam;
		}
	}
}
