package com.reactnativenavigation.test;


import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class MyNestedScrollViewManager extends ViewGroupManager<MyNestedScrollView> {

	@Override
	public String getName() {
		return "RCTMyNestedScrollView";
	}

	@Override
	protected MyNestedScrollView createViewInstance(ThemedReactContext reactContext) {
		return new MyNestedScrollView(reactContext);
	}

//	@ReactProp(name = "orientation")
//	public void setOrientation(MyNestedScrollView layout, @Nullable String orientation) {
//		if ("horizontal".equals(orientation)) {
//			layout.setOrientation(HORIZONTAL);
//		} else if ("vertical".equals(orientation)) {
//			layout.setOrientation(VERTICAL);
//		} else {
//			layout.setOrientation(HORIZONTAL);
//		}
//	}
}
