package com.reactnativenavigation.events;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.controllers.NavigationActivity;

import java.lang.ref.WeakReference;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * NavigationBroadcastReceiver
 */
public class NavigationBroadcastReceiver extends BroadcastReceiver {

    private WeakReference<NavigationActivity> mWeakReference;

    public NavigationBroadcastReceiver(NavigationActivity activity) {
        this.mWeakReference = new WeakReference<>(activity);
    }

    public void registerReceiver() {
        Activity activity = getActivity();
        if (activity != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(getFinishAction());
            LocalBroadcastManager.getInstance(activity).registerReceiver(this, intentFilter);
        }
    }

    @Override
    public void onReceive(Context context, final Intent intent) {
        String action = intent.getAction();
        final NavigationActivity activity = getActivity();
        //finish指定页面
        if (getFinishAction().equals(action)) {
            String simpleName = intent.getStringExtra(getFinishAction());
            if (activity != null
                    && activity.getClass().getSimpleName().equals(simpleName)) {
                activity.finish();
            }
        }
    }

    public static void killActivity(String simpleName) {
        Intent intent = new Intent(getFinishAction());
        intent.putExtra(getFinishAction(), simpleName);
        LocalBroadcastManager.getInstance(NavigationApplication.instance).sendBroadcast(intent);
    }

    private NavigationActivity getActivity() {
        return mWeakReference != null ? mWeakReference.get() : null;
    }

    private static String getFinishAction() {
        return String.format("%s_FINISH_ACTIVITY", NavigationApplication.instance.getPackageName());
    }
}
