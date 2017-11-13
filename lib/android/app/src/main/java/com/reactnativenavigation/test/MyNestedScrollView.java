package com.reactnativenavigation.test;


import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyNestedScrollView extends NestedScrollView {

	public MyNestedScrollView(Context context) {
		super(context);
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		super.addView(child, index, params);
	}

	@Override
	protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
		Log.i("NIGA", "child = " + child +  " width = " + widthUsed + " height = " + heightUsed);
		super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
	}
}
