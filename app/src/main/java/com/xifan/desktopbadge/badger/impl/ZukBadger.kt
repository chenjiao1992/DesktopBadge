package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 联想ZUK权限
 * <uses-permission android:name="android.permission.READ_APP_BADGE"></uses-permission>
 */
class ZukBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        val extra = Bundle()
        extra.putInt("app_badge_count", badgeCount)
        context.contentResolver.call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, extra)
    }
}
