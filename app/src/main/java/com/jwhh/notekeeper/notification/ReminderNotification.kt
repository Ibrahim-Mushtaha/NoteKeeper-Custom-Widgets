package com.jwhh.notekeeper.notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.ui.ItemsActivity


object ReminderNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private const val NOTIFICATION_TAG = "Reminder"

    const val REMINDER_CHANNEL = "reminders"

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     *
     * @see .cancel
     */
    fun notify(context: Context, titleText: String,
               noteText: String, number: Int) {

        val shareIntent = PendingIntent.getActivity(context,
            0,
            Intent.createChooser(Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT,
                    noteText
                ),
                "Share Note Reminder"),
            PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL)

            // Set appropriate defaults for the notification light, sound,
            // and vibration.
            .setDefaults(Notification.DEFAULT_ALL)

            // Set required fields, including the small icon, the
            // notification title, and text.
            .setSmallIcon(R.drawable.ic_stat_reminder)
            .setContentTitle(titleText)
            .setContentText(noteText)

            // All fields below this line are optional.

            // Use a default priority (recognized on devices running Android
            // 4.1 or later)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Set ticker text (preview) information for this notification.
            .setTicker(titleText)
            .setNumber(number)
            // Set the pending intent to be initiated when the user touches
            // the notification.
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, ItemsActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT))

            // Automatically dismiss the notification when it is touched.
            .setAutoCancel(true)

            // Add a share action
            .addAction(R.drawable.ic_share_black_24dp,
                "Share", shareIntent)

        notify(context, builder.build())
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private fun notify(context: Context, notification: Notification) {
        val nm = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification)
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification)
        }
    }

    /**
     * Cancels any notifications of this type previously shown using
     * [.notify].
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    fun cancel(context: Context) {
        val nm = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0)
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode())
        }
    }
}