package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.Context
import android.content.Intent
import com.sencent.badger.BadgerNumberManager
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 描述：xiaomi桌面角标
 * 官方文档:https://dev.mi.com/docs/appsmarket/technical_docs/badge/
 */
class XiaomiBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        try {
            if (!BadgerNumberManager.isShowNotification) {
                BadgerNumberManager.mNotification = getNotification(context, badgeCount)
            }
            val notification = BadgerNumberManager.mNotification ?: return
            val field = notification.javaClass.getDeclaredField("extraNotification")
            val extraNotification = field.get(notification)
            val method = extraNotification.javaClass.getDeclaredMethod("setMessageCount", Int::class.java)
            method.invoke(extraNotification, badgeCount)
            notify(context, notification, badgeCount)
        } catch (e: Exception) {
            e.printStackTrace()
            // 网上找的据说是miui 6之前的版本,没有miui6之前版本的小米手机不知道有没有效
            val localIntent = Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE")
            localIntent.putExtra("android.intent.extra.update_application_component_name", context.packageName + "/" + getLauncherClassName(context))
            localIntent.putExtra("android.intent.extra.update_application_message_text", (if (badgeCount == 0) "" else badgeCount).toString())
            context.sendBroadcast(localIntent)
        }

    }

}
