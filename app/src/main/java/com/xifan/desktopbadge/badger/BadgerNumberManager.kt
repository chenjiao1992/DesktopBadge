package com.sencent.badger

import android.app.Notification
import android.content.Context
import android.os.Build
import com.xifan.desktopbadge.badger.impl.DefaultBadger
import com.xifan.desktopbadge.badger.impl.GoogleBadger
import com.xifan.desktopbadge.badger.impl.HuaweiBadger
import com.xifan.desktopbadge.badger.impl.OPPOBadger
import com.xifan.desktopbadge.badger.impl.VivoBadger
import com.xifan.desktopbadge.badger.impl.XiaomiBadger
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/16 0016
 * 描述：桌面角标
 */
object BadgerNumberManager {
    private var TAG = "BadgerNumberManager"
    private var homeBadger: HomeBadger? = null
    var mNotification: Notification? = null
    var isShowNotification = false
    //测试数据,这里badgerCount只是一个假数据,为了让大家看到角标的效果,如果badgerCount是0就不显示角标
    var badgerCount = 0

    /**
     * 入口方法,所有业务层调用都是调用该方法,设置应用在桌面上显示的角标数字
     * @param badgerNum 显示的数字
     * @param notification 通知
     * @param isShowNotification 业务层传过来的通知是否是需要notif的
     */
    fun updateBadgeNumber(context: Context, notification: Notification?, isShowNotification: Boolean) {
        //todo 这里去获取im的未读消息数
        //        val accountId = AccountManager.accountId ?: return
        //        val badgerCount = ConversationBusiness2.getUnReadCount(accountId)
       //假数据
        badgerCount++


        this.mNotification = notification
        this.isShowNotification = isShowNotification
        val manufacturer = Build.MANUFACTURER
        try {
            when {
                manufacturer.equals("Huawei", ignoreCase = true) -> homeBadger = HuaweiBadger()
                manufacturer.equals("vivo", ignoreCase = true) -> homeBadger = VivoBadger()
                manufacturer.equals("OPPO", ignoreCase = true) -> homeBadger = OPPOBadger()
                manufacturer.equals("xiaomi", ignoreCase = true) -> homeBadger = XiaomiBadger()
                manufacturer.equals("google", ignoreCase = true) -> homeBadger = GoogleBadger()
                else -> homeBadger = DefaultBadger()
            }
            if (!manufacturer.equals("xiaomi", ignoreCase = true) && isShowNotification && notification != null) {
                homeBadger!!.notify(context, notification, badgerCount)
            }
            homeBadger!!.executeBadge(context, badgerCount, notification)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

