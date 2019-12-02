package com.example.mymoviecatalogueapp.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import java.util.*

class AlarmRepeatingReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_DAILY_REMINDER = "type_daily_reminder"
        const val TYPE_DAILY_MOVIE = "type_daily_movie"
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"

        private const val REQ_DAILY_REMINDER = 1000
        private const val REQ_DAILY_MOVIE = 1001
    }

    override fun onReceive(context: Context, intent: Intent) {
        val message: String
        val title: String

        val type = intent.getStringExtra(EXTRA_TYPE)

        if (type.equals(TYPE_DAILY_REMINDER, ignoreCase = false)) {
            title = context.getString(R.string.notification_title)
            message = context.getString(R.string.notification_message)
        } else {
            title = intent.getStringExtra(EXTRA_TITLE) ?: throw NullPointerException("Title null")
            message =
                intent.getStringExtra(EXTRA_MESSAGE) ?: throw NullPointerException("message null")
        }

        val notifId = intent.getIntExtra(EXTRA_ID, 0)

        showAlarmNotification(context, title, message, notifId)
    }

    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int
    ) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager_channel"

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_movie_black_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)

    }

    fun setDailyReminder(
        context: Context
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmRepeatingReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, TYPE_DAILY_REMINDER)
        intent.putExtra(EXTRA_ID, 100)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, REQ_DAILY_REMINDER, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, "Daily Reminder On", Toast.LENGTH_SHORT).show()

    }

    fun setDailyMovie(
        context: Context,
        movies: List<Movie>
    ) {

        var notifId = 101
        for (movie in movies) {

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmRepeatingReceiver::class.java)
            intent.putExtra(EXTRA_MESSAGE, movie.overview)
            intent.putExtra(EXTRA_TITLE, movie.title)
            intent.putExtra(EXTRA_TYPE, TYPE_DAILY_MOVIE)
            intent.putExtra(EXTRA_ID, notifId)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQ_DAILY_MOVIE,
                intent,
                0
            )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
//                2 * 60 * 1000,
                pendingIntent
            )
            notifId++

        }
        Toast.makeText(context, "Daily Movie ON", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(
        context: Context,
        type: String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmRepeatingReceiver::class.java)

        val requestCode = if (type.equals(TYPE_DAILY_REMINDER, ignoreCase = false))
            REQ_DAILY_REMINDER else REQ_DAILY_MOVIE

        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        if (requestCode == REQ_DAILY_REMINDER) {
            Toast.makeText(context, "Daily Reminder OFF", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Daily Movie OFF", Toast.LENGTH_SHORT).show()
        }

    }


}
