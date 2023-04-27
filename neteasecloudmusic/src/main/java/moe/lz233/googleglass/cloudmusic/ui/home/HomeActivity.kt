package moe.lz233.googleglass.cloudmusic.ui.home

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.glass.widget.CardBuilder
import kotlinx.coroutines.launch
import moe.lz233.googleglass.cloudmusic.logic.dao.UserDao
import moe.lz233.googleglass.cloudmusic.logic.network.CloudMusicNetwork
import moe.lz233.googleglass.cloudmusic.ui.daily.DailyActivity
import moe.lz233.googleglass.cloudmusic.ui.login.LoginActivity
import moe.lz233.googleglass.cloudmusic.ui.playlist.PlayListActivity
import moe.lz233.googleglass.cloudmusic.utils.ktx.adjustParam
import moe.lz233.googleglass.ui.BaseActivity
import moe.lz233.googleglass.ui.adapter.CardAdapter
import moe.lz233.googleglass.util.ktx.bitmap
import moe.lz233.googleglass.util.ktx.setActionBoundAdapter

class HomeActivity : BaseActivity() {

    private val cardAdapter by lazy {
        CardAdapter(
            mutableListOf(
                CardBuilder(this, CardBuilder.Layout.CAPTION),
                CardBuilder(this, CardBuilder.Layout.MENU).setText("我的歌单"),
                CardBuilder(this, CardBuilder.Layout.MENU).setText("每日推荐")
            ),
            mutableListOf(
                null,
                { PlayListActivity.actionStart(this) },
                { DailyActivity.actionStart(this) }
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardScrollerView.setActionBoundAdapter(cardAdapter)
        if (UserDao.isLogin) {
            launch {
                val accountInfoResponse = CloudMusicNetwork.getAccountInfo()
                Glide.with(this@HomeActivity)
                    .bitmap(accountInfoResponse.profile.backgroundUrl.adjustParam(640, 360)) {
                        cardAdapter.cards[0].addImage(it)
                        cardAdapter.notifyDataSetChanged()
                    }
                Glide.with(this@HomeActivity)
                    .bitmap(accountInfoResponse.profile.avatarUrl.adjustParam(100, 100)) {
                        cardAdapter.cards[0].setIcon(it)
                        cardAdapter.cards[0].setText(accountInfoResponse.profile.nickName)
                        cardAdapter.cards[0].setFootnote(accountInfoResponse.profile.bio)
                        cardAdapter.notifyDataSetChanged()
                    }
            }
        } else {
            LoginActivity.actionStart(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) recreate()
        }
    }
}