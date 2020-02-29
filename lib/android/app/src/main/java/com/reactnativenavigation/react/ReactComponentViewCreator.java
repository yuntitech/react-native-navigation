package com.reactnativenavigation.react;

import android.app.Activity;

import com.facebook.react.ReactInstanceManager;
import com.reactnativenavigation.viewcontrollers.ReactViewCreator;
import com.reactnativenavigation.viewcontrollers.IReactView;

public class ReactComponentViewCreator implements ReactViewCreator {
	private ReactInstanceManager reactInstanceManager;

	public ReactComponentViewCreator(final ReactInstanceManager reactInstanceManager) {
		this.reactInstanceManager = reactInstanceManager;
	}

	@Override
	public IReactView create(final Activity activity, final String componentId, final String componentName) {
        if (useReactView(componentName)) {
            return new ReactView(activity, reactInstanceManager, componentId, componentName);
        }
        return new RNGestureHandlerReactView(activity, reactInstanceManager, componentId, componentName);
	}

    private boolean useReactView(String componentName) {
        return componentName != null && componentName.endsWith("NativeLightBox");
    }
}
