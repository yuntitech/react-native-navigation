package com.reactnativenavigation.controllers;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.events.Event;
import com.reactnativenavigation.events.EventBus;
import com.reactnativenavigation.events.JsDevReloadEvent;
import com.reactnativenavigation.events.ModalDismissedEvent;
import com.reactnativenavigation.events.NavigationBroadcastReceiver;
import com.reactnativenavigation.events.Subscriber;
import com.reactnativenavigation.layouts.BottomTabsLayout;
import com.reactnativenavigation.layouts.Layout;
import com.reactnativenavigation.layouts.LayoutFactory;
import com.reactnativenavigation.params.ActivityParams;
import com.reactnativenavigation.params.AppStyle;
import com.reactnativenavigation.params.ContextualMenuParams;
import com.reactnativenavigation.params.FabParams;
import com.reactnativenavigation.params.LightBoxParams;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.SlidingOverlayParams;
import com.reactnativenavigation.params.SnackbarParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.params.parsers.ScreenParamsParser;
import com.reactnativenavigation.react.ReactGateway;
import com.reactnativenavigation.screens.NavigationType;
import com.reactnativenavigation.screens.Screen;
import com.reactnativenavigation.utils.OrientationHelper;
import com.reactnativenavigation.utils.ReflectionUtils;
import com.reactnativenavigation.views.SideMenu.Side;
import com.reactnativenavigation.params.parsers.LightBoxParamsParser;

import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;

import java.util.List;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler, Subscriber, PermissionAwareActivity {

    public static final String NAME = "NavigationActivity";
    /**
     * Although we start multiple activities, we make sure to pass Intent.CLEAR_TASK | Intent.NEW_TASK
     * So that we actually have only 1 instance of the activity running at one time.
     * We hold the currentActivity (resume->pause) so we know when we need to destroy the javascript context
     * (when currentActivity is null, ie pause and destroy was called without resume).
     * This is somewhat weird, and in the future we better use a single activity with changing contentView similar to ReactNative impl.
     * Along with that, we should handle commands from the bridge using onNewIntent
     */
    static NavigationActivity currentActivity;
    private static Promise startAppPromise;

    private ActivityParams activityParams;
    private ModalController modalController;
    private Layout layout;
    @Nullable
    private PermissionListener mPermissionListener;
    private NavigationBroadcastReceiver receiver;
    private MyBroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NavigationApplication.instance.getReactGateway().hasStartedCreatingContext() ||
                getIntent() == null ||
                getIntent().getBundleExtra("ACTIVITY_PARAMS_BUNDLE") == null) {
            SplashActivity.start(this);
            finish();
            return;
        }
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        NavigationBroadcastReceiver.killActivity(getClass().getSimpleName());
        receiver = new NavigationBroadcastReceiver(this);
        receiver.registerReceiver();
        activityParams = NavigationCommandsHandler.parseActivityParams(getIntent());
        disableActivityShowAnimationIfNeeded();
        setOrientation();
        createModalController();
        createLayout();
        NavigationApplication.instance.getActivityCallbacks().onActivityCreated(this, savedInstanceState);
        mBroadcastReceiver = new MyBroadcastReceiver();
        LocalBroadcastManager.getInstance(NavigationApplication.instance)
                .registerReceiver(mBroadcastReceiver, new IntentFilter(NAME));
    }

    private void setOrientation() {
        OrientationHelper.setOrientation(this, AppStyle.appStyle.orientation);
    }

    private void disableActivityShowAnimationIfNeeded() {
        if (!activityParams.animateShow) {
            overridePendingTransition(0, 0);
        }
    }

    private void createModalController() {
        modalController = new ModalController(this);
    }

    private void createLayout() {
        layout = LayoutFactory.create(this, activityParams);
        if (hasBackgroundColor()) {
            layout.asView().setBackgroundColor(AppStyle.appStyle.screenBackgroundColor.getColor());
        }
        setContentView(layout.asView());
    }

    private boolean hasBackgroundColor() {
        return AppStyle.appStyle.screenBackgroundColor != null &&
                AppStyle.appStyle.screenBackgroundColor.hasColor();
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationApplication.instance.getActivityCallbacks().onActivityStarted(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFinishing() || !NavigationApplication.instance.isReactContextInitialized()) {
            return;
        }

        currentActivity = this;
        IntentDataHandler.onResume(getIntent());
        getReactGateway().onResumeActivity(this, this);
        resolveStartAppPromiseOnActivityResumed();
        NavigationApplication.instance.getActivityCallbacks().onActivityResumed(this);
        EventBus.instance.register(this);
        IntentDataHandler.onPostResume(getIntent());
        NavigationApplication.instance.getEventEmitter().sendActivityResumed(getCurrentlyVisibleEventId());
    }

    private void resolveStartAppPromiseOnActivityResumed() {
        if (startAppPromise != null) {
            startAppPromise.resolve(true);
            startAppPromise = null;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getReactGateway().onNewIntent(intent);
        NavigationApplication.instance.getActivityCallbacks().onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentActivity = null;
        IntentDataHandler.onPause(getIntent());
        getReactGateway().onPauseActivity(this);
        NavigationApplication.instance.getActivityCallbacks().onActivityPaused(this);
        EventBus.instance.unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearStartAppPromise();
        NavigationApplication.instance.getActivityCallbacks().onActivityStopped(this);
    }

    private void clearStartAppPromise() {
        if (startAppPromise != null) {
            startAppPromise = null;
        }
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
        if (mBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(NavigationApplication.instance)
                    .unregisterReceiver(mBroadcastReceiver);
        }
        destroyLayouts();
        destroyJsIfNeeded();
        NavigationApplication.instance.getActivityCallbacks().onActivityDestroyed(this);
        super.onDestroy();
    }

    private void destroyLayouts() {
        if (modalController != null) {
            modalController.destroy();
        }
        if (layout != null) {
            layout.destroy();
            layout = null;
        }
    }

    private void destroyJsIfNeeded() {
        if (currentActivity == null || currentActivity.isFinishing()) {
            getReactGateway().onDestroyApp(this);
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        if (layout != null && !layout.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        //TAB页返回
        if (isTabScreen() && Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) {
            if (System.currentTimeMillis() - mExitTime > 2000L) {
                Toast toast = Toast.makeText(NavigationApplication.instance, "再按一次退出程序", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                mExitTime = System.currentTimeMillis();
                return;
            }
        }
        if (layout != null && layout.handleBackInJs()) {
            return;
        }
        if (getReactGateway().isInitialized()) {
            getReactGateway().onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private boolean isTabScreen() {
        String curScreenId = layout != null ? layout.getCurrentlyVisibleScreenId() : null;
        if (activityParams != null
                && activityParams.tabParams != null
                && curScreenId != null) {
            for (ScreenParams each : activityParams.tabParams) {
                if (curScreenId.equals(each.getScreenInstanceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getReactGateway().onActivityResult(requestCode, resultCode, data);
        NavigationApplication.instance.getActivityCallbacks().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        NavigationApplication.instance.getActivityCallbacks().onKeyUp(keyCode, event);
        return getReactGateway().onKeyUp(getCurrentFocus(), keyCode) || super.onKeyUp(keyCode, event);
    }

    private ReactGateway getReactGateway() {
        return NavigationApplication.instance.getReactGateway();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        OrientationHelper.onConfigurationChanged(newConfig);
        NavigationApplication.instance.getActivityCallbacks().onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    void push(ScreenParams params, Promise onPushComplete) {
        if (modalController.containsNavigator(params.getNavigatorId())) {
            modalController.push(params, onPushComplete);
        } else {
            layout.push(params, onPushComplete);
        }
    }

    void pop(ScreenParams params) {
        if (modalController.containsNavigator(params.getNavigatorId())) {
            modalController.pop(params);
        } else {
            layout.pop(params);
        }
    }

    void popToRoot(ScreenParams params) {
        if (modalController.containsNavigator(params.getNavigatorId())) {
            modalController.popToRoot(params);
        } else {
            layout.popToRoot(params);
        }
    }

    void newStack(ScreenParams params) {
        if (modalController.containsNavigator(params.getNavigatorId())) {
            modalController.newStack(params);
        } else {
            layout.newStack(params);
        }
    }

    void showModal(ScreenParams screenParams) {
        Screen previousScreen = layout.getCurrentScreen();
        NavigationApplication.instance.getEventEmitter().sendWillDisappearEvent(previousScreen.getScreenParams(), NavigationType.ShowModal);
        NavigationApplication.instance.getEventEmitter().sendDidDisappearEvent(previousScreen.getScreenParams(), NavigationType.ShowModal);
        modalController.showModal(screenParams);
    }

    void dismissTopModal(ScreenParams params) {
        modalController.dismissTopModal(params);
    }

    void dismissAllModals() {
        modalController.dismissAllModals();
    }

    public void showLightBox(LightBoxParams params) {
        layout.showLightBox(params);
    }

    public void dismissLightBox() {
        layout.dismissLightBox();
    }

    //TODO all these setters should be combined to something like setStyle
    void setTopBarVisible(String screenInstanceId, boolean hidden, boolean animated) {
        layout.setTopBarVisible(screenInstanceId, hidden, animated);
        modalController.setTopBarVisible(screenInstanceId, hidden, animated);
    }

    void setBottomTabsVisible(boolean hidden, boolean animated) {
        if (layout instanceof BottomTabsLayout) {
            ((BottomTabsLayout) layout).setBottomTabsVisible(hidden, animated);
        }
    }

    void setTitleBarTitle(String screenInstanceId, String title) {
        layout.setTitleBarTitle(screenInstanceId, title);
        modalController.setTitleBarTitle(screenInstanceId, title);
    }

    public void setTitleBarSubtitle(String screenInstanceId, String subtitle) {
        layout.setTitleBarSubtitle(screenInstanceId, subtitle);
        modalController.setTitleBarSubtitle(screenInstanceId, subtitle);
    }

    void setTitleBarButtons(String screenInstanceId, String navigatorEventId, List<TitleBarButtonParams> titleBarButtons) {
        layout.setTitleBarRightButtons(screenInstanceId, navigatorEventId, titleBarButtons);
        modalController.setTitleBarRightButtons(screenInstanceId, navigatorEventId, titleBarButtons);
    }

    void setTitleBarLeftButton(String screenInstanceId, String navigatorEventId, TitleBarLeftButtonParams titleBarLeftButton) {
        layout.setTitleBarLeftButton(screenInstanceId, navigatorEventId, titleBarLeftButton);
        modalController.setTitleBarLeftButton(screenInstanceId, navigatorEventId, titleBarLeftButton);
    }

    void setScreenFab(String screenInstanceId, String navigatorEventId, FabParams fab) {
        layout.setFab(screenInstanceId, navigatorEventId, fab);
        modalController.setFab(screenInstanceId, navigatorEventId, fab);
    }

    public void setScreenStyle(String screenInstanceId, Bundle styleParams) {
        layout.updateScreenStyle(screenInstanceId, styleParams);
        modalController.updateScreenStyle(screenInstanceId, styleParams);
    }

    public void toggleSideMenuVisible(boolean animated, Side side) {
        layout.toggleSideMenuVisible(animated, side);
    }

    public void setSideMenuVisible(boolean animated, boolean visible, Side side) {
        layout.setSideMenuVisible(animated, visible, side);
    }

    public void setSideMenuEnabled(boolean enabled, Side side) {
        layout.setSideMenuEnabled(enabled, side);
    }

    public void selectTopTabByTabIndex(String screenInstanceId, int index) {
        layout.selectTopTabByTabIndex(screenInstanceId, index);
        modalController.selectTopTabByTabIndex(screenInstanceId, index);
    }

    public void selectTopTabByScreen(String screenInstanceId) {
        layout.selectTopTabByScreen(screenInstanceId);
        modalController.selectTopTabByScreen(screenInstanceId);
    }

    public void selectBottomTabByTabIndex(Integer index) {
        if (layout instanceof BottomTabsLayout) {
            ((BottomTabsLayout) layout).selectBottomTabByTabIndex(index);
        }
    }

    public void selectBottomTabByNavigatorId(String navigatorId) {
        if (layout instanceof BottomTabsLayout) {
            ((BottomTabsLayout) layout).selectBottomTabByNavigatorId(navigatorId);
        }
    }

    public void setBottomTabBadgeByIndex(Integer index, String badge) {
        if (layout instanceof BottomTabsLayout) {
            ((BottomTabsLayout) layout).setBottomTabBadgeByIndex(index, badge);
        }
    }

    public void setBottomTabBadgeByNavigatorId(String navigatorId, String badge) {
        if (layout instanceof BottomTabsLayout) {
            ((BottomTabsLayout) layout).setBottomTabBadgeByNavigatorId(navigatorId, badge);
        }
    }

    public void setBottomTabButtonByIndex(Integer index, ScreenParams params) {
        if (layout instanceof BottomTabsLayout) {
            ((BottomTabsLayout) layout).setBottomTabButtonByIndex(index, params);
        }
    }

    public void setBottomTabButtonByNavigatorId(String navigatorId, ScreenParams params) {
        if (layout instanceof BottomTabsLayout) {
            ((BottomTabsLayout) layout).setBottomTabButtonByNavigatorId(navigatorId, params);
        }
    }

    public void showSlidingOverlay(SlidingOverlayParams params) {
        if (modalController.isShowing()) {
            modalController.showSlidingOverlay(params);
        } else {
            layout.showSlidingOverlay(params);
        }
    }

    public void hideSlidingOverlay() {
        if (modalController.isShowing()) {
            modalController.hideSlidingOverlay();
        } else {
            layout.hideSlidingOverlay();
        }
    }

    public void showSnackbar(SnackbarParams params) {
        layout.showSnackbar(params);
    }

    public void dismissSnackbar() {
        layout.dismissSnackbar();
    }

    public void showContextualMenu(String screenInstanceId, ContextualMenuParams params, Callback onButtonClicked) {
        if (modalController.isShowing()) {
            modalController.showContextualMenu(screenInstanceId, params, onButtonClicked);
        } else {
            layout.showContextualMenu(screenInstanceId, params, onButtonClicked);
        }
    }

    public void dismissContextualMenu(String screenInstanceId) {
        if (modalController.isShowing()) {
            modalController.dismissContextualMenu(screenInstanceId);
        } else {
            layout.dismissContextualMenu(screenInstanceId);
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType().equals(ModalDismissedEvent.TYPE)) {
            handleModalDismissedEvent();
        } else if (event.getType().equals(JsDevReloadEvent.TYPE)) {
            postHandleJsDevReloadEvent();
        }
    }

    private void handleModalDismissedEvent() {
        if (!modalController.isShowing()) {
            layout.onModalDismissed();
            OrientationHelper.setOrientation(this, AppStyle.appStyle.orientation);
        }
    }

    public Window getScreenWindow() {
        return modalController.isShowing() ? modalController.getWindow() : getWindow();
    }

    private void postHandleJsDevReloadEvent() {
        NavigationApplication.instance.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                layout.destroy();
                modalController.destroy();

                Object devSupportManager = ReflectionUtils.getDeclaredField(getReactGateway().getReactInstanceManager(), "mDevSupportManager");
                if (ReflectionUtils.getDeclaredField(devSupportManager, "mRedBoxDialog") != null) { // RN >= 0.52
                    ReflectionUtils.setField(devSupportManager, "mRedBoxDialog", null);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        mPermissionListener = listener;
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        NavigationApplication.instance.getActivityCallbacks().onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionListener != null && mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            mPermissionListener = null;
        }
    }

    public String getCurrentlyVisibleScreenId() {
        return modalController.isShowing() ? modalController.getCurrentlyVisibleScreenId() : layout.getCurrentlyVisibleScreenId();
    }

    public String getCurrentlyVisibleEventId() {
        return modalController.isShowing() ? modalController.getCurrentlyVisibleEventId() : layout.getCurrentScreen().getNavigatorEventId();
    }

    public static void setStartAppPromise(Promise promise) {
        NavigationActivity.startAppPromise = promise;
    }

    private String getScreenId() {
        if (activityParams != null && activityParams.screenParams != null) {
            return activityParams.screenParams.screenId;
        }
        return null;
    }

    public boolean isLoadingScreen() {
        return "cn.bookln.InitialScreen".equals(getScreenId());
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (NAME.equals(intent.getAction()) && intent.getStringExtra("action") != null) {
                String action = intent.getStringExtra("action");
                Bundle params;
                switch (action) {
                    case "showLightBox":
                        LightBoxParams lbp = new LightBoxParamsParser(intent.getExtras()).parse();
                        showLightBox(lbp);
                        break;
                    case "dismissLightBox":
                        dismissLightBox();
                        break;
                    case "showModal":
                        params = intent.getExtras();
                        if (params != null) {
                            showModal(ScreenParamsParser.parse(params));
                        }
                        break;
                    case "dismissTopModal":
                        params = intent.getExtras();
                        if (params != null) {
                            dismissTopModal(ScreenParamsParser.parse(params));
                        }
                        break;
                    case "dismissAllModals":
                        dismissAllModals();
                        break;
                    case "pop":
                        params = intent.getExtras();
                        if (params != null) {
                            pop(ScreenParamsParser.parse(params));
                        }
                        break;
                }

            }
        }
    }
}
