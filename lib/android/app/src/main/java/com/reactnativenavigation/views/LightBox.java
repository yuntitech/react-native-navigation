package com.reactnativenavigation.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatDialog;

import com.reactnativenavigation.viewcontrollers.ViewController;

public class LightBox extends AppCompatDialog implements DialogInterface.OnDismissListener {

    private Runnable onDismissListener;
    private ViewController mViewController;
    private LightBoxDelegate mDelegate;

    public LightBox(Activity activity, ViewController viewController, LightBoxDelegate delegate, Runnable onDismissListener) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = delegate;
        this.onDismissListener = onDismissListener;
        this.mViewController = viewController;
        setOnDismissListener(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        FrameLayout container = new FrameLayout(activity);
        ViewGroup content = viewController.getView();
        container.addView(content);
        setContentView(container);
        setCancelable(true);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        destroy();
    }

    @Override
    public void onBackPressed() {
        if (mDelegate != null) {
            mDelegate.onBackPressed();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        onDismissListener.run();
    }

    public void destroy() {
        mViewController.destroy();
        dismiss();
    }

    public interface LightBoxDelegate {
        void onBackPressed();
    }
}
