package com.rizki.githubuserapp.broadcast

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.rizki.githubuserapp.MainActivity
import com.rizki.githubuserapp.R
import java.util.*

class AlarmBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val notifId = if (type.equals(TYPE_REPEATING, ignoreCase = true)) ID_REPEATING else return
        
        showAlarmNotification(context, notifId, message)
    }

    private fun showAlarmNotification(context: Context, notifId: Int, message: String?) {
        val channelId = "Channel_1"
        val channelName = "Daily Alarm"

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_alarm_24)
            .setContentTitle(context.getString(R.string.title_alarm_preference))
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                        channelName,
                        NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    fun setRepeatingAlarm(context: Context?, type: String, message: String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
            putExtra(EXTRA_TYPE, type)
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, "Repeating Alarm Setup", Toast.LENGTH_SHORT).show()

    }

    fun cancelAlarm(context: Context?, type: String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val requestCode = if (type.equals(TYPE_REPEATING, ignoreCase = true)) ID_REPEATING else return
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Repeating Alarm Cancelled", Toast.LENGTH_SHORT).show()
    }

    fun isAlarmSet(context: Context, type: String): Boolean {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val requestCode = if (type.equals(TYPE_REPEATING, ignoreCase = true)) ID_REPEATING else 0
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_NO_CREATE
            ) != null
    }

}