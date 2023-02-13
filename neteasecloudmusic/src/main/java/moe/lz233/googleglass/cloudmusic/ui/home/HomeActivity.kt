package moe.lz233.googleglass.cloudmusic.ui.home

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.glass.widget.CardBuilder
import kotlinx.coroutines.launch
import moe.lz233.googleglass.cloudmusic.logic.dao.UserDao
import moe.lz233.googleglass.cloudmusic.logic.network.CloudMusicNetwork
import moe.lz233.googleglass.cloudmusic.ui.BaseActivity
import moe.lz233.googleglass.cloudmusic.ui.adapter.CardAdapter
import moe.lz233.googleglass.cloudmusic.ui.login.LoginActivity
import moe.lz233.googleglass.cloudmusic.utils.ktx.bitmap
import moe.lz233.googleglass.cloudmusic.utils.ktx.setActionBoundAdapter
import moe.lz233.googleglass.util.LogUtil

class HomeActivity : BaseActivity() {

    private val cardAdapter by lazy {
        CardAdapter(
            listOf(
                CardBuilder(this, CardBuilder.Layout.CAPTION),
                CardBuilder(this, CardBuilder.Layout.MENU).setText("我的歌单"),
                CardBuilder(this, CardBuilder.Layout.MENU).setText("每日推荐"),
                CardBuilder(this, CardBuilder.Layout.MENU).setText("关于")
            ),
            listOf(
                { LogUtil.toast("test") },
                { Unit },
                { Unit }
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardScrollerView.setActionBoundAdapter(cardAdapter)
        if (UserDao.isLogin) {
            launch {
                val accountInfoResponse = CloudMusicNetwork.getAccountInfo()
                cardAdapter.cards[0].setText(accountInfoResponse.profile.nickName)
                cardAdapter.cards[0].setFootnote(accountInfoResponse.profile.bio)
                Glide.with(this@HomeActivity)
                    .bitmap(accountInfoResponse.profile.backgroundUrl) {
                        cardAdapter.cards[0].addImage(it)
                        cardAdapter.notifyDataSetChanged()
                    }
                Glide.with(this@HomeActivity)
                    .bitmap(accountInfoResponse.profile.avatarUrl) {
                        cardAdapter.cards[0].setIcon(it)
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