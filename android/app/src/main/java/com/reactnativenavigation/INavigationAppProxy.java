package com.reactnativenavigation;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.reactnativenavigation.bridge.EventEmitter;
import com.reactnativenavigation.controllers.ActivityCallbacks;
import com.reactnativenavigation.react.ReactGateway;

import java.util.List;

/**
 * Created by kangqiang on 2018/5/28.
 */
public interface INavigationAppProxy {

    void startReactContextOnceInBackgroundAndExecuteJS();

    void runOnMainThread(Runnable runnable);

    void runOnMainThread(Runnable runnable, long delay);

    ReactGateway getReactGateway();

    ActivityCallbacks getActivityCallbacks();

    void setActivityCallbacks(ActivityCallbacks activityLifecycleCallbacks);

    boolean isReactContextInitialized();

    void onReactInitialized(ReactContext reactContext);

    ReactNativeHost getReactNativeHost();

    EventEmitter getEventEmitter();

    UIManagerModule getUiManagerModule();

    /**
     * @see ReactNativeHost#getJSMainModuleName()
     */
    String getJSMainModuleName();

    /**
     * @see ReactNativeHost#getJSBundleFile()
     */
    String getJSBundleFile();

    /**
     * @see ReactNativeHost#getBundleAssetName()
     */
    String getBundleAssetName();

    abstract boolean isDebug();

    boolean clearHostOnActivityDestroy();

    abstract List<ReactPackage> createAdditionalReactPackages();
}
