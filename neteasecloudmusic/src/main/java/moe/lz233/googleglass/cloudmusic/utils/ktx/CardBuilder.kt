package moe.lz233.googleglass.cloudmusic.utils.ktx

import com.google.android.glass.widget.CardBuilder

fun CardBuilder.getImageCount() = this.javaClass.getDeclaredMethod("getImageCount").invoke(this) as Int

fun CardBuilder.hasImage() = this.getImageCount() > 0