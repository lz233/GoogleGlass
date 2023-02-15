package com.zhy.mediaplayer_exo.playermanager.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.google.android.glass.timeline.LiveCard
import com.zhy.mediaplayer_exo.BuildConfig
import com.zhy.mediaplayer_exo.R
import com.zhy.mediaplayer_exo.playermanager.MediaSwitchTrackChange
import com.zhy.mediaplayer_exo.playermanager.PlaylistItem
import com.zhy.mediaplayer_exo.playermanager.activity.MenuActivity
import com.zhy.mediaplayer_exo.playermanager.manager.MediaManager
import com.zhy.mediaplayer_exo.playermanager.musicbroadcast.MusicBroadcast
import moe.lz233.googleglass.util.LogUtil
import moe.lz233.googleglass.util.ktx.bitmap


@SuppressLint("RemoteViewLayout")
class MediaForegroundService : Service() {

    private val liveCard by lazy { LiveCard(this, BuildConfig.LIBRARY_PACKAGE_NAME) }
    private val liveCardView by lazy { RemoteViews(packageName, R.layout.livecard) }

    companion object {
        @JvmStatic
        val EXTRA_NOTIFICATION_DATA = "EXTRA_NOTIFICATION_DATA"

        @JvmStatic
        val musicBroadcast = MusicBroadcast()

        @JvmStatic
        var startService = 0

        /**
         * 判断某个服务是否正在运行的方法
         *
         * @param mContext
         * @param serviceName
         * 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
         * @return true代表正在运行，false代表服务没有正在运行
         */
        fun isServiceWork(
            mContext: Context,
            serviceName: String
        ): Boolean {
            return startService > 0
        }
    }

    override fun onCreate() {
        super.onCreate()
        MediaManager.addMediaSwitchChange(object : MediaSwitchTrackChange {
            override fun onTracksChange(playlistItem: PlaylistItem) {
                LogUtil.d(playlistItem.name)
                Glide.with(this@MediaForegroundService)
                    .bitmap("${playlistItem.coverUrl}?param=640y360") {
                        liveCardView.setTextViewText(R.id.card_title, playlistItem.name)
                        liveCardView.setTextViewText(R.id.card_text, playlistItem.artistName)
                        liveCardView.setImageViewBitmap(R.id.card_imageview, it)
                        liveCard.setAction(
                            PendingIntent.getActivity(
                                this@MediaForegroundService,
                                0,
                                Intent(this@MediaForegroundService, MenuActivity::class.java),
                                0
                            )
                        )
                        liveCard.setViews(liveCardView)
                        if (!liveCard.isPublished) liveCard.publish(LiveCard.PublishMode.REVEAL)
                    }
            }
        }
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            startService++
            registerReceiver(musicBroadcast, IntentFilter(MusicBroadcast.ACTION_MUSIC_BROADCASET_UPDATE))
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return MBinder()
    }

    inner class MBinder : Binder() {

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        if (liveCard.isPublished) liveCard.unpublish()
        startService = 0

        //移除通知栏
        stopForeground(true)
        stopSelf()
        unregisterReceiver(musicBroadcast)
        MediaManager.getSimpleExoPlayer().pause()
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
        println("MediaForegroundService onDestroy")
//        android.os.Process.killProcess(android.os.Process.myPid())

        super.onDestroy()
    }


}