package com.reactnativenavigation.react.coordinator;


import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;

import com.reactnativenavigation.R;

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

}
