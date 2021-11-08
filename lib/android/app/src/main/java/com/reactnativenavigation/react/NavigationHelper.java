package com.reactnativenavigation.react;

import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.options.Options;
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController;

public class NavigationHelper {

    /**
     * 播放页面controller
     */
    public static ViewController<?> saveController = null;
    public static boolean isPictureInPicture = false;

    public static String[] mSupportPictureInPictureComponent ={} ;

    /**
     * 初始化支持的画中画的页面
     * @param componentNames
     */
    public static void initSupportPictureInPictureComponent(String[] componentNames){
        mSupportPictureInPictureComponent = componentNames ;
    }

    /**
     * 即将跳转到下一个页面
     * @param viewController
     */
    public static void willPush(ViewController<?> viewController){
        if (isSupportPictureForComponentName(viewController)) {
            if(saveController != null){
                saveController.destroy();
            }
            saveController = viewController ;
        }
    }

    /**
     * 开始画中画
     * @param activity
     * @param componentId
     * @param commandListener
     */
    public static void startPictureInPicture(NavigationActivity activity,String componentId,CommandListener commandListener){
        NavigationHelper.isPictureInPicture = true;
        activity.runOnUiThread(() ->
            activity.getNavigator().pop(componentId, Options.EMPTY, commandListener)
        ) ;
    }

    /**
     * 通过ViewController查询该页面是否有画中画功能
     * @param viewController
     * @return
     */
    public static boolean isSupportPictureForComponentName(ViewController<?> viewController){
        try {
            if (mSupportPictureInPictureComponent != null && viewController != null) {
                for (int length = mSupportPictureInPictureComponent.length, i = 0; i < length; i++) {
                    if (mSupportPictureInPictureComponent[i].equals(viewController.getCurrentComponentName())) {
                        return true;
                    }
                }
            }
            return false;
        }catch (Exception e){
            return false ;
        }
    }

}
