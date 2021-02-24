package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val CHANNEL_ID = "channel id"
    val CHANNEL_NAME = "channel name"
    val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 3 calling the channel creating function
        createNotificationChannel()


        //5 to be able to open our app by taping on the notification we need to create a pending intent. What is happening behind the sccenes is that a system app is handling the notification we send an to allow it to open our app we need to create a pending intent.

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }


        //4 create the notification and pass it into the notification manager
        // at this point the notification is showing in the notifications bar but when you click on it it it not taking me to the application.

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Cool Notification")
            .setContentText("This is the content")
            .setSmallIcon(R.drawable.ic_baseline_star_border_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // this gives priority over other notifications
            .setContentIntent(pendingIntent) // setting the pending intent
            .setAutoCancel(true) // makes the notification disappear from the bar once opened in the app
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

         button.setOnClickListener {
             notificationManager.notify(NOTIFICATION_ID, notification)
         }







    }
    // 1 we need to create a notification channel where we will post the notifications, this channel will hold the configurations of our notifications.
    // Once you set the notification with the channel you can not change it afterwards

    private fun createNotificationChannel(){
        // before android Oreo, we did not need to create the notification channel but after we need.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH).apply {   // this gives importance... pop up with sound etc..

                    // changing the led light when the notification comes and the screen is off
                    lightColor = Color.GREEN
                enableLights(true)
                }

            // 2 after we create the chanel we need to create a notification manager which will create the channel in the system
            val manager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
            manager.createNotificationChannel(channel)
        }

    }

}