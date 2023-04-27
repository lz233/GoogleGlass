package moe.lz233.googleglass.util.ktx

import com.google.android.glass.widget.CardBuilder

fun CardBuilder.getImageCount() = this.javaClass.getDeclaredMethod("getImageCount").invoke(this) as Int

fun CardBuilder.hasImage() = this.getImageCount() > 0