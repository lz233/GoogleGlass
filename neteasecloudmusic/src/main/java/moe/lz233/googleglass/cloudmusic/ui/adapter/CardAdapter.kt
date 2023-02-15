package moe.lz233.googleglass.cloudmusic.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.google.android.glass.widget.CardBuilder
import com.google.android.glass.widget.CardScrollAdapter

open class CardAdapter(val cards: MutableList<CardBuilder>, val actions: MutableList<(() -> Unit)?> = mutableListOf()) : CardScrollAdapter() {

    override fun getCount() = cards.size

    override fun getItem(position: Int) = cards[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = cards[position].getView(convertView, parent)

    override fun getPosition(item: Any) = cards.indexOf(item)

    override fun getItemViewType(position: Int) = cards[position].itemViewType
}