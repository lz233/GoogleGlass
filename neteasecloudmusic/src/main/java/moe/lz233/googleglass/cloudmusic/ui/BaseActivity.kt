package moe.lz233.googleglass.cloudmusic.ui

import android.app.Activity
import android.os.Bundle
import com.google.android.glass.widget.CardScrollView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

open class BaseActivity : Activity(), CoroutineScope by MainScope() {

    val cardScrollerView by lazy { CardScrollView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cardScrollerView)
    }

    override fun onResume() {
        super.onResume()
        cardScrollerView.activate()
    }

    override fun onPause() {
        super.onPause()
        cardScrollerView.deactivate()
    }
}