package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.Context
import android.content.Intent
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 描述：HTC桌面角标
 */
class HtcBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        try {
            val intent = Intent("android.intent.action.BADGE_COUNT_UPDATE")
            intent.putExtra("badge_count", badgeCount)
            intent.putExtra("badge_count_package_name", context.packageName)
            intent.putExtra("badge_count_class_name", getLauncherClassName(context))
            context.sendBroadcast(intent)
        } catch (e: Exception) {
        }

    }
}
