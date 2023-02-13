package moe.lz233.googleglass.cloudmusic.utils.ktx

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun RequestManager.bitmap(url: String, success: (Bitmap) -> Unit) {
    this
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                success.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })
}