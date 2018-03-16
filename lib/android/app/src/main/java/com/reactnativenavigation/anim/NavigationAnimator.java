package com.reactnativenavigation.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;

@SuppressWarnings("ResourceType")
public class NavigationAnimator extends BaseAnimator {

    public NavigationAnimator(Context context) {
        super(context);
    }

    public void animatePush(final View view, @Nullable final AnimatorListenerAdapter animationListener) {
//        view.setVisibility(View.INVISIBLE);
        AnimatorSet set;
        if (options.push.hasValue()) {
            set = options.push.getAnimation(view);
        } else {
            set = getDefaultPushAnimation(view);
        }
        set.addListener(animationListener);
        set.start();
        new Handler(Looper.getMainLooper()).postDelayed(() -> view.setVisibility(View.VISIBLE), 100);
    }

    public void animatePop(View view, @Nullable final AnimationListener animationListener) {
        AnimatorSet set;
        if (options.pop.hasValue()) {
            set = options.pop.getAnimation(view);
        } else {
            set = getDefaultPopAnimation(view);
        }
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd();
                }
            }
        });
        set.start();
    }
}
