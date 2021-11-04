package com.jwhh.notekeeper.widgets

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.ui.NoteActivity

/**
 * Implementation of App Widget functionality.
 */
class NoteKeeperAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            //refresh all your widgets
            val manager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, NoteKeeperAppWidget::class.java)
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(componentName),
                R.id.notes_list)
        }

        super.onReceive(context, intent)
    }

    companion object {

        internal fun updateAppWidget(context: Context,
                                     appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val widgetText = "NOTES"
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.note_keeper_app_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)
            views.setRemoteAdapter(R.id.notes_list,
                Intent(context, AppWidgetRemoteViewsService::class.java))

            val intent = Intent(context, NoteActivity::class.java)

            val pendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

            views.setPendingIntentTemplate(R.id.notes_list, pendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun sendRefreshBroadcast(context: Context) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            intent.component = ComponentName(context, NoteKeeperAppWidget::class.java)
            context.sendBroadcast(intent)
        }
    }
}