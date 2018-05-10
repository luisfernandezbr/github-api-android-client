package br.com.luisfernandez.github.client.misc

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by luisfernandez on 10/05/18.
 */
class ImageLoader {
    companion object {
        fun loadImage(url: String, imageView: ImageView) {
            val context = imageView.context
            Glide.with(context)
                    .load(url)
                    .into(imageView)
        }
    }
}