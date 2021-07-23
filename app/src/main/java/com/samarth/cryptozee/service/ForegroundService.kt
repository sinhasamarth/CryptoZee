package com.samarth.cryptozee.service

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.samarth.cryptozee.MainActivity
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.localStorage.entities.AlertEntity
import com.samarth.cryptozee.utils.CONSTANTS.Companion.ForegroundService_FLAG
import com.samarth.cryptozee.viewModelShared

class ForegroundService:Service() {
    val p = 100
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel("my_service", "My Background Service")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Destination", "Alert")
        val pendingIntent = PendingIntent.getActivity(this , 10 , intent , p)
        val notification: Notification = Notification.Builder(this,"my_service")
            .setContentTitle(getText(R.string.alerts))
            .setContentText(getText(R.string.price_alert))
            .setSmallIcon(R.drawable.ic_checked)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(ForegroundService_FLAG,notification)
        startApiCalls()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startApiCalls() {
        viewModelShared.allAlertCoin.value?.forEach { data  ->
                getPriceAlert(data.coinId , data.price)
        }
    }

    private fun getPriceAlert(coinId: String, price: String?) {
            viewModelShared.getSingleCoinDetail(coinId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }



}