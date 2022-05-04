package com.example.button_tap_2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var b = findViewById<Button>(R.id.button2);
        b.setOnClickListener(View.OnClickListener {
            /*
            // Explicit Intent, you need to associate an activity using FQN see the below line.
            var intent:Intent = Intent(this, Class.forName("com.example.button_tap_2.MainActivity"))
            // startActivity is used to invoke other activity associated to an Intent object.
            startActivity(intent)
             */

            /*
           // The below code deals with Requesting Permission for the app.
                    if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                        Log.i("Permission Check", "Yes")
                        // call to dialer app to make call
                        val callIntent = Intent().apply {
                            action = Intent.ACTION_CALL
                        }
                        callIntent.setData(Uri.parse("tel:+919876543210"))
                        startActivity(callIntent)
                    }else{
                        Log.i("Permission Check", "No")
                        Log.i("Permission Check", "Requesting Permission")
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),10)
                    }
        */
            // Creating a notification on Button Tap.
            // Ref: https://developer.android.com/training/notify-user/build-notification
            createNotificationChannelAndNotifcation()
        }
        )
    }

    private fun createNotificationChannelAndNotifcation() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = "Channel 1 notifcations"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        // Built a notifcation object
        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            //.setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }

        var nmc= NotificationManagerCompat.from(this)
        nmc.notify(2,builder.build())

    }

    // App permission callback
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            if (grantResults.isEmpty()) {
                Log.i("Permission Check", "Missed the Permission Request")
            }
            if ((grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
            ) {
                // Permission is granted. Continue the action or workflow
                // in your app.
                val callIntent = Intent().apply {
                    action = Intent.ACTION_CALL
                }
                callIntent.setData(Uri.parse("tel:+919876543210"))
                startActivity(callIntent)
            } else {
                //Creating Toast message
                Toast.makeText(this, "You denied permission", Toast.LENGTH_LONG).show()
            }
        }
    }
}