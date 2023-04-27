package moe.lz233.googleglass.cloudmusic.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.glass.widget.CardBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.lz233.googleglass.cloudmusic.logic.dao.UserDao
import moe.lz233.googleglass.cloudmusic.logic.network.CloudMusicNetwork
import moe.lz233.googleglass.ui.BaseActivity
import moe.lz233.googleglass.ui.adapter.CardAdapter
import moe.lz233.googleglass.util.LogUtil
import moe.lz233.googleglass.util.QRCodeUtil
import moe.lz233.googleglass.util.ktx.setActionBoundAdapter

class LoginActivity : BaseActivity() {

    private val cardAdapter by lazy {
        CardAdapter(
            mutableListOf(
                CardBuilder(this, CardBuilder.Layout.COLUMNS)
                    .setText("扫描二维码登录")
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardScrollerView.setActionBoundAdapter(cardAdapter)
        startLogin()
    }

    private fun startLogin() {
        launch {
            val keyResponse = CloudMusicNetwork.getKey(3)
            cardAdapter.cards[0].clearImages()
            cardAdapter.cards[0].addImage(QRCodeUtil.createQRCodeBitmap("https://music.163.com/login?codekey=${keyResponse.key}", 240, 360))
            cardAdapter.notifyDataSetChanged()
            LogUtil.d(keyResponse.key)
            check@ while (true) {
                val checkResponse = CloudMusicNetwork.checkQrStatus(keyResponse.key, 3)
                LogUtil.d(checkResponse.code)
                when (checkResponse.code) {
                    800 -> {
                        LogUtil.toast(checkResponse.message)
                        startLogin()
                        break@check
                    }
                    803 -> {
                        val userStatusResponse = CloudMusicNetwork.getAccountInfo()
                        LogUtil.d(userStatusResponse)
                        UserDao.id = userStatusResponse.profile.userId
                        UserDao.type = userStatusResponse.profile.userType
                        UserDao.isLogin = true
                        setResult(RESULT_OK, null)
                        finish()
                        break@check
                    }
                }
                delay(3000)
            }
        }
    }

    companion object {
        fun actionStart(activity: Activity) = activity.startActivityForResult(Intent(activity, LoginActivity::class.java), 1)
    }
}