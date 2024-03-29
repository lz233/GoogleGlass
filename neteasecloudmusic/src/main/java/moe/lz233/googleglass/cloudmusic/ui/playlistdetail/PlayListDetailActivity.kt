package moe.lz233.googleglass.cloudmusic.ui.playlistdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.zhy.mediaplayer_exo.playermanager.manager.MediaManager
import kotlinx.coroutines.launch
import moe.lz233.googleglass.cloudmusic.logic.model.meta.Music
import moe.lz233.googleglass.cloudmusic.logic.network.CloudMusicNetwork
import moe.lz233.googleglass.cloudmusic.utils.ktx.adjustParam
import moe.lz233.googleglass.cloudmusic.utils.ktx.toCards
import moe.lz233.googleglass.cloudmusic.utils.ktx.toPlayListItem
import moe.lz233.googleglass.ui.BaseActivity
import moe.lz233.googleglass.ui.adapter.CardAdapter
import moe.lz233.googleglass.util.ktx.bitmap
import moe.lz233.googleglass.util.ktx.hasImage
import moe.lz233.googleglass.util.ktx.setActionBoundAdapter
import moe.lz233.googleglass.util.ktx.setOnItemSelectedListener

class PlayListDetailActivity : BaseActivity() {

    private lateinit var musicList: List<Music>
    private val cardAdapter by lazy { CardAdapter(mutableListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardScrollerView.setActionBoundAdapter(cardAdapter)
        launch {
            val playlistDetailResponse = CloudMusicNetwork.getPlaylistDetail(intent.getLongExtra("playlistId", 415524508))
            musicList = playlistDetailResponse.playlist.tracks
            cardAdapter.cards.addAll(musicList.toCards())
            cardAdapter.notifyDataSetChanged()
        }
        cardScrollerView.setOnItemClickListener { parent, view, position, id ->
            MediaManager.playlist(musicList.toPlayListItem(), position)
        }
        cardScrollerView.setOnItemSelectedListener { selected, parent, view, position, id ->
            if (selected && !cardAdapter.cards[position!!].hasImage()) {
                Glide.with(this)
                    .bitmap(musicList[position].album.picUrl.adjustParam(640, 360)) {
                        cardAdapter.cards[position].addImage(it)
                        cardAdapter.notifyDataSetChanged()
                    }
            }
        }
    }

    companion object {
        fun actionStart(context: Context, playlistId: Long) = context.startActivity(Intent(context, PlayListDetailActivity::class.java).apply { putExtra("playlistId", playlistId) })
    }
}