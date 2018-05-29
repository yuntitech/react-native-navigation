package com.reactnativenavigation;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.reactnativenavigation.bridge.EventEmitter;
import com.reactnativenavigation.controllers.ActivityCallbacks;
import com.reactnativenavigation.react.NavigationReactGateway;
import com.reactnativenavigation.react.ReactGateway;

import java.util.List;

public class NavigationApplication implements ReactApplication {

    public static NavigationApplication instance;
    private static INavigationAppProxy mINavigationAppProxy;

    public static void initINavigationAppProxy(INavigationAppProxy iNavigationAppProxy) {
        mINavigationAppProxy = iNavigationAppProxy;
        if (instance == null) {
            synchronized (NavigationApplication.class) {
                instance = new NavigationApplication();
            }
        }
    }

    public void startReactContextOnceInBackgroundAndExecuteJS() {
        if (mINavigationAppProxy != null) {
            mINavigationAppProxy.startReactContextOnceInBackgroundAndExecuteJS();
        }
    }

    public void runOnMainThread(Runnable runnable) {
        if (mINavigationAppProxy != null) {
            mINavigationAppProxy.runOnMainThread(runnable);
        }
    }

    public void runOnMainThread(Runnable runnable, long delay) {
        if (mINavigationAppProxy != null) {
            mINavigationAppProxy.runOnMainThread(runnable, delay);
        }
    }

    public ReactGateway getReactGateway() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.getReactGateway();
        }
        return null;
    }

    public ActivityCallbacks getActivityCallbacks() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.getActivityCallbacks();
        }
        return null;
    }

    protected void setActivityCallbacks(ActivityCallbacks activityLifecycleCallbacks) {
        if (mINavigationAppProxy != null) {
            mINavigationAppProxy.setActivityCallbacks(activityLifecycleCallbacks);
        }
    }

    public boolean isReactContextInitialized() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.isReactContextInitialized();
        }
        return false;
    }

    public void onReactInitialized(ReactContext reactContext) {
        if (mINavigationAppProxy != null) {
            mINavigationAppProxy.onReactInitialized(reactContext);
        }
        // nothing
    }

    public ReactNativeHost getReactNativeHost() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.getReactNativeHost();
        }
        return null;
    }

    public EventEmitter getEventEmitter() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.getEventEmitter();
        }
        return null;
    }

    public UIManagerModule getUiManagerModule() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.getUiManagerModule();
        }
        return getReactGateway()
                .getReactInstanceManager()
                .getCurrentReactContext()
                .getNativeModule(UIManagerModule.class);
    }

    /**
     * @see ReactNativeHost#getJSMainModuleName()
     */
    @Nullable
    public String getJSMainModuleName() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.getJSMainModuleName();
        }
        return null;
    }

    /**
     * @see ReactNativeHost#getJSBundleFile()
     */
    @Nullable
    public String getJSBundleFile() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.getJSBundleFile();
        }
        return null;
    }

    /**
     * @see ReactNativeHost#getBundleAssetName()
     */
    @Nullable
    public String getBundleAssetName() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.getBundleAssetName();
        }
        return null;
    }

    public boolean isDebug() {
        return true;
    }

    public boolean clearHostOnActivityDestroy() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.clearHostOnActivityDestroy();
        }
        return true;
    }

    @Nullable
    public List<ReactPackage> createAdditionalReactPackages() {
        if (mINavigationAppProxy != null) {
            return mINavigationAppProxy.createAdditionalReactPackages();
        }
        return null;
    }

    public Application context() {
        if (mINavigationAppProxy != null) {
            return (Application) mINavigationAppProxy;
        }
        return null;
    }
}
