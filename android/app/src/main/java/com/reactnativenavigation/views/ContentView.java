package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.params.NavigationParams;
import com.reactnativenavigation.screens.SingleScreen;
import com.reactnativenavigation.views.utils.ViewMeasurer;

public class ContentView extends ReactRootView implements ReactRootView.ReactRootViewEventListener, ViewTreeObserver.OnGlobalLayoutListener {
    private final String screenId;
    private final NavigationParams navigationParams;
    private Bundle initialProps;

    private SingleScreen.OnDisplayListener onDisplayListener;
    protected ViewMeasurer viewMeasurer;
    private boolean isAttachedToReactInstance;

    public void setOnDisplayListener(SingleScreen.OnDisplayListener onDisplayListener) {
        this.onDisplayListener = onDisplayListener;
    }

    public ContentView(Context context, String screenId, NavigationParams navigationParams) {
        this(context, screenId, navigationParams, Bundle.EMPTY);
    }

    public ContentView(Context context, String screenId, NavigationParams navigationParams, Bundle initialProps) {
        super(context);
        this.screenId = screenId;
        this.navigationParams = navigationParams;
        this.initialProps = initialProps;
        attachToJS();
        viewMeasurer = new ViewMeasurer();
        setEventListener(this);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void setViewMeasurer(ViewMeasurer viewMeasurer) {
        this.viewMeasurer = viewMeasurer;
    }

    private void attachToJS() {
        navigationParams.toBundle().putAll(initialProps);
        startReactApplication(NavigationApplication.instance.getReactGateway().getReactInstanceManager(),
                screenId,
                createInitialParams()
        );
    }

    private Bundle createInitialParams() {
        final Bundle params = new Bundle();
        params.putAll(navigationParams.toBundle());
        params.putAll(initialProps);
        return params;
    }

    public String getNavigatorEventId() {
        return navigationParams.navigatorEventId;
    }

    public void unmountReactView() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        setEventListener(null);
        unmountReactApplication();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = viewMeasurer.getMeasuredHeight(heightMeasureSpec);
        setMeasuredDimension(viewMeasurer.getMeasuredWidth(widthMeasureSpec), measuredHeight);
    }

    @Override
    public void onAttachedToReactInstance(ReactRootView rootView) {
        isAttachedToReactInstance = true;
    }

    @Override
    public void onGlobalLayout() {
        if (isAttachedToReactInstance && onDisplayListener != null && getChildCount() > 0) {
            onDisplayListener.onDisplay();
            onDisplayListener = null;
        }
    }
}
