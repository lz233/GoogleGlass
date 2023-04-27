package moe.lz233.googleglass.util.ktx

import android.view.View
import android.widget.AdapterView
import com.google.android.glass.widget.CardScrollView
import moe.lz233.googleglass.ui.adapter.CardAdapter

fun CardScrollView.setActionBoundAdapter(adapter: CardAdapter) {
    this.adapter = adapter
    this.setOnItemClickListener { parent, view, position, id -> adapter.actions[position]?.invoke() }
}

fun CardScrollView.setOnItemSelectedListener(block: (selected: Boolean, parent: AdapterView<*>?, view: View?, position: Int?, id: Long?) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            block(true, parent, view, position, id)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            block(false, null, null, null, null)
        }
    }
}