package com.reactnativenavigation.test;


import android.support.annotation.Nullable;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;

public class MyLinearLayoutManager extends ViewGroupManager<MyLinearLayout> {

	@Override
	public String getName() {
		return "RCTMyLinearLayout";
	}

	@Override
	protected MyLinearLayout createViewInstance(ThemedReactContext reactContext) {
		return new MyLinearLayout(reactContext);
	}

	@ReactProp(name = "orientation")
	public void setOrientation(MyLinearLayout layout, @Nullable String orientation) {
		if ("horizontal".equals(orientation)) {
			layout.setOrientation(HORIZONTAL);
		} else if ("vertical".equals(orientation)) {
			layout.setOrientation(VERTICAL);
		} else {
			layout.setOrientation(HORIZONTAL);
		}
	}
}
