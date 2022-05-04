package com.example.button_tap_2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private lateinit var orient:String
    private lateinit var tv:TextView
    private var count:Int = 0
    private var CountP:Int = 1
    private var CountL:Int = 0
    private var orientation = Configuration.ORIENTATION_PORTRAIT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orientation = this.resources.configuration.orientation
        val currOrientation = this.resources.configuration.orientation

        val b = findViewById<Button>(R.id.button)
        tv = findViewById(R.id.textView)
        when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> {}
            Surface.ROTATION_90 -> {tv.append("Left Rotation\n")}
            Surface.ROTATION_180 -> {}
            Surface.ROTATION_270 -> {tv.append("Right Rotation\n")}
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("message","portrait")
            orient = "Portrait mode"
            tv.append("Portrait mode")

        } else {
            Log.i("message","landscape")
            tv.append("Landscape mode")
            orient = "Landscape mode"
        }
        tv.append("\nThe rotation count is $count")

        b.setOnClickListener {
            tv.append("\nLocked Rotation")
            requestedOrientation = if (currOrientation == Configuration.ORIENTATION_PORTRAIT) {
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            } else {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }

            val builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Screen Rotation")
                .setContentText("Screen is Locked")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(2, builder.build())
            }


        }
        createNotificationChannel()


    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = getString(R.string.channel_name)
        val descriptionText = "Channel 1 notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        // Built a notification object
        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Screen Rotation")
            .setContentText("Portrait : "+CountP.toString()+"Landscape : "+CountL.toString())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            //.setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        count += 1
        Log.i("message","value of count variable is $count")
        outState.putInt("countValue",count)


        Log.i("message","onSaveInstanceState One Arg")
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            CountL+=1
        } else {
            CountP+=1
        }
        outState.putInt("countValueL",CountL)
        outState.putInt("countValueP",CountP)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("message","onRestoreInstanceState")
        count = savedInstanceState.getInt("countValue")
        CountL = savedInstanceState.getInt("countValueL")
        CountP = savedInstanceState.getInt("countValueP")

        Log.i("message","value of count variable restored is $count")
        tv.append("\nThe rotation count is $count")
        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Screen Rotation")
            .setContentText("Portrait : "+CountP.toString()+"Landscape : "+CountL.toString())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            //.setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }


    }

}