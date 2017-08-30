package com.reactnativenavigation.views.utils;

import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcherListener;
import com.facebook.react.views.scroll.ScrollEvent;
import com.facebook.react.views.scroll.ScrollEventType;
import com.reactnativenavigation.utils.ReflectionUtils;

/**
 * ReactScrollView scroll event listener. Listens to scroll events before they pass over the bridge.
 * Usage: NavigationApplication.instance.getReactGateway().getEventDispatcher().addListener(...);
 */
public class ScrollEventListener implements EventDispatcherListener {
    private OnVerticalScrollListener verticalScrollListener;
    private int prevScrollY = -1;

    @SuppressWarnings("WeakerAccess")
    public interface OnVerticalScrollListener {
        void onVerticalScroll(int scrollY);
    }

    public ScrollEventListener(OnVerticalScrollListener verticalScrollListener) {
        this.verticalScrollListener = verticalScrollListener;
    }

    @Override
    public void onEventDispatch(Event event) {
        if (event instanceof ScrollEvent) {
            handleScrollEvent((ScrollEvent) event);
        }
    }

    private void handleScrollEvent(ScrollEvent event) {
        try {
            if (ScrollEventType.SCROLL.getJSEventName().equals(event.getEventName())) {
                int scrollY = (int) ReflectionUtils.getDeclaredField(event, "mScrollY");
                if (scrollY != prevScrollY) {
                    prevScrollY = scrollY;
                }
                verticalScrollListener.onVerticalScroll(scrollY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
