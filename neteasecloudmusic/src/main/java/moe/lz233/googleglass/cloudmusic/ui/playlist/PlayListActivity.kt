package moe.lz233.googleglass.cloudmusic.ui.playlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import moe.lz233.googleglass.cloudmusic.logic.dao.UserDao
import moe.lz233.googleglass.cloudmusic.logic.model.meta.PlayList
import moe.lz233.googleglass.cloudmusic.logic.network.CloudMusicNetwork
import moe.lz233.googleglass.cloudmusic.ui.BaseActivity
import moe.lz233.googleglass.cloudmusic.ui.adapter.CardAdapter
import moe.lz233.googleglass.cloudmusic.ui.playlistdetail.PlayListDetailActivity
import moe.lz233.googleglass.cloudmusic.utils.ktx.*
import moe.lz233.googleglass.util.ktx.bitmap

class PlayListActivity : BaseActivity() {

    private val playLists = mutableListOf<PlayList>()
    private val cardAdapter by lazy { CardAdapter(mutableListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardScrollerView.setActionBoundAdapter(cardAdapter)

        launch {
            val userPlaylistResponse = CloudMusicNetwork.getUserPlaylist(UserDao.id)
            var pinnedPlaylistIndex = 0
            userPlaylistResponse.playlists.forEach {
                if (it.creator.userId == UserDao.id || (it.specialType == 100 && it.name.contains("雷达")))
                    playLists.add(pinnedPlaylistIndex++, it)
                else
                    playLists.add(it)
            }
            cardAdapter.cards.addAll(playLists.toCards())
            cardAdapter.notifyDataSetChanged()
        }
        cardScrollerView.setOnItemClickListener { parent, view, position, id ->
            PlayListDetailActivity.actionStart(this, playLists[position].id)
        }
        cardScrollerView.setOnItemSelectedListener { selected, parent, view, position, id ->
            if (selected && !cardAdapter.cards[position!!].hasImage()) {
                Glide.with(this)
                    .bitmap(playLists[position].coverImgUrl.adjustParam(640, 360)) {
                        cardAdapter.cards[position].addImage(it)
                        cardAdapter.notifyDataSetChanged()
                    }
            }
        }
    }

    companion object {
        fun actionStart(context: Context) = context.startActivity(Intent(context, PlayListActivity::class.java))
    }
}