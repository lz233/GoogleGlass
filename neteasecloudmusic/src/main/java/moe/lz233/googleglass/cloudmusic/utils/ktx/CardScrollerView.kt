package moe.lz233.googleglass.cloudmusic.utils.ktx

import com.google.android.glass.widget.CardScrollView
import moe.lz233.googleglass.cloudmusic.ui.adapter.CardAdapter

fun CardScrollView.setActionBoundAdapter(adapter: CardAdapter) {
    this.adapter = adapter
    this.setOnItemClickListener { parent, view, position, id -> adapter.actions[position]?.invoke() }
}