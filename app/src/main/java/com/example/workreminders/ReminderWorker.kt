package com.example.workreminders

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_DAILY"
        private const val NOTIFICATION_ID = 1
    }

    override fun doWork(): Result {
        Log.d("ReminderWorker", "Công việc bắt đầu")
        val workList = getDataFromProvider(applicationContext)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Công việc hôm nay")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true)

        if (workList.isNotEmpty()) {
            val notificationContent = StringBuilder()
            for (work in workList) {
                notificationContent.append("${work.workName}\n ${work.workTime}")
            }
            notificationBuilder.setContentText(notificationContent.toString())
        } else {
            notificationBuilder.setContentText("Không có công việc nào hôm nay")
        }

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }
        Log.d("ReminderWorker", "Công việc hoàn thành")
        return Result.success()
    }
}