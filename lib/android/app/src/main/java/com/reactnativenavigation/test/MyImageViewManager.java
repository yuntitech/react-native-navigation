package com.reactnativenavigation.test;


import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class MyImageViewManager extends SimpleViewManager<MyImageView> {

	@Override
	public String getName() {
		return "RCTMyImageView";
	}

	@Override
	protected MyImageView createViewInstance(ThemedReactContext reactContext) {
		return new MyImageView(reactContext);
	}
}
