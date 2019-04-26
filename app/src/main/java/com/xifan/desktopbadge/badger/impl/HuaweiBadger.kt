package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 描述：华为桌面角标 ,华为有对外的接口说明书,说明书见doc目下的badge.pdf
 */
class HuaweiBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        try {
            val bunlde = Bundle()
            val launchClassName = context.packageManager.getLaunchIntentForPackage(context.packageName)!!.component!!.className
            bunlde.putString("package", context.packageName)
            bunlde.putString("class", launchClassName)
            bunlde.putInt("badgenumber", badgeCount)
            context.contentResolver.call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde)
        } catch (e: Exception) {
        }

    }

}
