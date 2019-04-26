package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.util.Log
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 描述：google桌面角标
 */
class GoogleBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            Log.e("GoogleMoldeImpl", NOTIFICATION_ERROR)
        }
        val intent = Intent("android.intent.action.BADGE_COUNT_UPDATE")
        intent.putExtra("badge_count", badgeCount)
        intent.putExtra("badge_count_package_name", context.packageName)
        intent.putExtra("badge_count_class_name", getLauncherClassName(context))
        context.sendBroadcast(intent)
    }

    companion object {
        private val NOTIFICATION_ERROR = "google not support before API O"
    }

}
