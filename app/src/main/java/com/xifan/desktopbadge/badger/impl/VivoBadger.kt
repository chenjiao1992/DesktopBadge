package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.Context
import android.content.Intent
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 描述：vivo桌面角标
 */
class VivoBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        try {
            val intent = Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM")
            intent.putExtra("packageName", context.packageName)
            intent.putExtra("className", getLauncherClassName(context))
            intent.putExtra("notificationNum", badgeCount)
            context.sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}