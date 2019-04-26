package com.xifan.desktopbadge.badger

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.xifan.desktopbadge.MainActivity
import com.xifan.desktopbadge.R


/**
 *  Create by chenjiao at 2019/4/17 0017
 *  描述：桌面角标抽象类
 */
abstract class HomeBadger {
    private var notificationManager: NotificationManager? = null
    //桌面角标的通知的id必须是相同的,所以id在这里定义好
    private var NOTIFICATION_ID = 341
    private val TAG = "xiaomiBadger"

    abstract fun executeBadge(context: Context, badgeCount: Int, notification: Notification?)

    protected fun getLauncherClassName(context: Context): String {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val packageManager = context.packageManager
        val resolveInfoList = packageManager.queryIntentActivities(intent, 0)
        var launcherClassName = ""
        for (resolveInfo in resolveInfoList) {
            if (context != null && context.packageName.equals(resolveInfo.activityInfo.applicationInfo.packageName, ignoreCase = true)) {
                launcherClassName = resolveInfo.activityInfo.name
                break
            }
        }
        return launcherClassName
    }

    protected fun getNotification(context: Context, badgeCount: Int): Notification {
        if (notificationManager == null) {
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //8.0之后添加角标需要NotificationChannel
                val channel = NotificationChannel("badge", "badge", NotificationManager.IMPORTANCE_LOW)
                channel.setShowBadge(true)
                (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
            }
        }

        return NotificationCompat.Builder(context, "badge")
            .setContentTitle("你的app名称")
            .setContentText("您有${badgeCount}条新消息")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setNumber(badgeCount)
            .setContentIntent(PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT))
            .setAutoCancel(true)
            .build()
    }

    /**
     * 发送通知
     */
    fun notify(context: Context, notification: Notification?, badgeCount: Int) {
        if(notification==null){
            return
        }
        if (notificationManager == null) {
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        notificationManager!!.cancel(TAG, NOTIFICATION_ID)
        if (badgeCount > 0) {
            notificationManager!!.notify(TAG, NOTIFICATION_ID, notification)
        }
    }

}