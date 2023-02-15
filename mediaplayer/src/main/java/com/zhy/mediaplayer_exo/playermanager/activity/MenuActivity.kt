package com.zhy.mediaplayer_exo.playermanager.activity

import android.app.Activity
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.zhy.mediaplayer_exo.R
import com.zhy.mediaplayer_exo.playermanager.MediaPlayerExoPlayMode
import com.zhy.mediaplayer_exo.playermanager.manager.MediaManager
import kotlin.system.exitProcess

class MenuActivity : Activity() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        openOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (MediaManager.isPlaying()) {
            menu.add("暂停").icon = ContextCompat.getDrawable(this, R.drawable.ic_pause)
        } else {
            menu.add("播放").icon = ContextCompat.getDrawable(this, R.drawable.ic_play)
        }
        menu.add("上一首").icon = ContextCompat.getDrawable(this, R.drawable.ic_skip_previous)
        menu.add("下一首").icon = ContextCompat.getDrawable(this, R.drawable.ic_skip_next)
        when (MediaManager.getCurrentPlayMode()) {
            MediaPlayerExoPlayMode.MEDIA_LIST_LOOP -> {
                menu.add("随机播放").icon = ContextCompat.getDrawable(this, R.drawable.ic_shuffle)
                menu.add("单曲循环").icon = ContextCompat.getDrawable(this, R.drawable.ic_loop)
            }
            MediaPlayerExoPlayMode.MEDIA_ALONE_LOOP -> {
                menu.add("顺序播放").icon = ContextCompat.getDrawable(this, R.drawable.ic_audiotrack)
                menu.add("随机播放").icon = ContextCompat.getDrawable(this, R.drawable.ic_shuffle)
            }
            MediaPlayerExoPlayMode.MEDIA_LSIT_RANDOM -> {
                menu.add("顺序播放").icon = ContextCompat.getDrawable(this, R.drawable.ic_audiotrack)
                menu.add("单曲循环").icon = ContextCompat.getDrawable(this, R.drawable.ic_loop)
            }
        }
        menu.add("关闭").icon = ContextCompat.getDrawable(this, R.drawable.ic_close)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "暂停" -> MediaManager.pause()
            "播放" -> MediaManager.play()
            "上一首" -> MediaManager.playLast()
            "下一首" -> MediaManager.playNext()
            "顺序播放" -> MediaManager.switchPlayMode(MediaPlayerExoPlayMode.MEDIA_LIST_LOOP)
            "随机播放" -> MediaManager.switchPlayMode(MediaPlayerExoPlayMode.MEDIA_LSIT_RANDOM)
            "单曲循环" -> MediaManager.switchPlayMode(MediaPlayerExoPlayMode.MEDIA_ALONE_LOOP)
            "关闭" -> {
                finish()
                exitProcess(0)
            }
        }
        return true
    }

    override fun onOptionsMenuClosed(menu: Menu?) {
        super.onOptionsMenuClosed(menu)
        finish()
    }
}