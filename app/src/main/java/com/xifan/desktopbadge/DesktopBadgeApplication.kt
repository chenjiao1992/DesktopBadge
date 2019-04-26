package com.xifan.desktopbadge

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.sencent.badger.BadgerNumberManager

/**
 *  Create by chenjiao at 2019/4/26 0026
 *  描述：
 */

class DesktopBadgeApplication : Application(), Application.ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }
    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        //因为桌面角标时必须我们回到桌面才能看到效果,所以在app压后台去设置角标比较好
        if (AppUtil.isBackground(this)) {
            BadgerNumberManager.updateBadgeNumber(this, null, false)
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }

}