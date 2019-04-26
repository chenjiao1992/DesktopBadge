package com.xifan.desktopbadge.badger.impl

import android.app.Notification
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.xifan.desktopbadge.badger.HomeBadger

/**
 * Create by chenjiao at 2019/4/17 0017
 * 描述:三星桌面角标
 */
class SumsungBadger : HomeBadger() {
    override fun executeBadge(context: Context, badgeCount: Int, notification: Notification?) {
        val uri = Uri.parse("content://com.sec.badge/apps")
        val columnId = "_id"
        val columnPackage = "package"
        val columnClass = "class"
        val columnBadgeCount = "badgeCount"
        var cursor: Cursor? = null
        try {
            val contentResolver = context.contentResolver
            cursor = contentResolver.query(uri, arrayOf(columnId), "$columnPackage=?", arrayOf(context.packageName), null)

            if (cursor == null || !cursor.moveToFirst()) {
                val contentValues = ContentValues()
                contentValues.put(columnPackage, context.packageName)
                contentValues.put(columnClass, getLauncherClassName(context))
                contentValues.put(columnBadgeCount, badgeCount)
                contentResolver.insert(uri, contentValues)
            } else {
                val idColumnIndex = cursor.getColumnIndex(columnId)
                val contentValues = ContentValues()
                contentValues.put(columnBadgeCount, badgeCount)
                contentResolver.update(uri, contentValues, "$columnId=?", arrayOf(cursor.getInt(idColumnIndex).toString()))
            }
        } catch (e: Exception) {
            DefaultBadger().executeBadge(context, badgeCount,notification)
        } finally {
            cursor?.close()
        }
    }
}
