package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 描述：oppo桌面角标 ,oppo需要发邮件申请,只支持IM类型app,审核通过后会回复一个带有开发文档的附件,附件见doc目录下的ColorOs桌面角标功能接入指南
 * 注意:oppo发送邮件申请通过后,只是初步的,还要参照开发文档与oppo那边进行调试,调试通过后oppo的开发才会将我们的app加入到白名单去,这样才算完成了oppo的桌面角标配置
 */
class OPPOBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        try {
            val extras = Bundle()
            extras.putInt("app_badge_count", badgeCount)
            context.contentResolver.call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, extras)
        } catch (e: Exception) {
            DefaultBadger().executeBadge(context, badgeCount,notification)
        }

    }
}
