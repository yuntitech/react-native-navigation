package com.reactnativenavigation.react.coordinator;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.reactnativenavigation.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FloatingActionButtonView extends FloatingActionButton {

	public FloatingActionButtonView(Context context) {
		super(context);

		CoordinatorLayout.LayoutParams params =
				new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
		params.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.margin);
		params.topMargin = context.getResources().getDimensionPixelSize(R.dimen.margin);
		params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.margin);
		params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.margin);
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
