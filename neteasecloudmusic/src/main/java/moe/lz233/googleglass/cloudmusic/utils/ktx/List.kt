package moe.lz233.googleglass.cloudmusic.utils.ktx

import com.google.android.glass.widget.CardBuilder
import com.zhy.mediaplayer_exo.playermanager.PlaylistItem
import com.zhy.mediaplayer_exo.playermanager.meta.PlayListItem
import moe.lz233.googleglass.App
import moe.lz233.googleglass.cloudmusic.logic.model.meta.Artist
import moe.lz233.googleglass.cloudmusic.logic.model.meta.Music
import moe.lz233.googleglass.cloudmusic.logic.model.meta.PlayList

fun List<Music>.toPlayListItem() = mutableListOf<PlaylistItem>().apply {
    this@toPlayListItem.forEach {
        add(
            PlayListItem(
                it.id,
                it.mvId,
                it.name,
                it.artists.map { artist -> artist.id },
                it.artists.toArtistName(),
                it.album.id,
                it.album.name,
                it.album.picUrl,
                it.id.getSongUrl()
            )
        )
    }
}

inline fun <reified T> List<T>.toCards() = mutableListOf<CardBuilder>().apply {
    when (T::class) {
        Music::class -> this@toCards.forEach {
            add(
                CardBuilder(App.context, CardBuilder.Layout.CAPTION)
                    .setText((it as Music).name)
                    .setFootnote(it.artists.toArtistName())
            )
        }
        PlayList::class -> this@toCards.forEach {
            add(
                CardBuilder(App.context, CardBuilder.Layout.CAPTION)
                    .setText((it as PlayList).name)
                    .setFootnote(it.creator.nickName)
            )
        }
        else -> throw Throwable("Unexpected card type")
    }
}

fun List<Artist>.toArtistName() = StringBuilder().apply {
    this@toArtistName.forEach {
        append(it.name)
        append('/')
    }
    deleteCharAt(lastIndex)
}.toString()

fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int) =
    if (toIndex > size)
        this.subList(fromIndex, size)
    else
        this.subList(fromIndex, toIndex)