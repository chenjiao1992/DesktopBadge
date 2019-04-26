package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.AsyncQueryHandler
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 索尼权限
 * <uses-permission android:name="com.sonymobile.home.permission.PROVIDER_INSERT_BADGE"></uses-permission>
 * <uses-permission android:name="com.sonyericsson.home.permission.BROADCAST_BADGE"></uses-permission>
 * <uses-permission android:name="com.sonyericsson.home.action.UPDATE_BADGE"></uses-permission>
 */
class SonyBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        if (asyncQueryHandler == null) {
            asyncQueryHandler = object : AsyncQueryHandler(context.contentResolver) {
            }
        }
        try {
            //官方给出方法
            val contentValues = ContentValues()
            contentValues.put("badge_count", badgeCount)
            contentValues.put("package_name", context.packageName)
            contentValues.put("activity_name", getLauncherClassName(context))
            asyncQueryHandler!!.startInsert(0, null, Uri.parse("content://com.sonymobile.home.resourceprovider/badge"), contentValues)
        } catch (e: Exception) {

            val intent = Intent("com.sonyericsson.home.action.UPDATE_BADGE")
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", badgeCount > 0)
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", getLauncherClassName(context))
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", (if (badgeCount > 0) badgeCount else "").toString())
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.packageName)
            context.sendBroadcast(intent)
        }

    }

    companion object {
        private var asyncQueryHandler: AsyncQueryHandler? = null
    }

}
