package br.com.luisfernandez.github.client.android

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

/**
 * Created by luisfernandez on 10/05/18.
 */
class ImageLoader {
    companion object {
        fun loadImage(url: String, imageView: ImageView) {
            val context = imageView.context
            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions().placeholder(getRandomDrawableColor()))
                    .into(imageView)
        }

        private fun getRandomDrawableColor(): ColorDrawable {
            val idx = Random().nextInt(vibrantLightColorList.size)
            return vibrantLightColorList[idx]
        }

        private val vibrantLightColorList = arrayOf(
                ColorDrawable(Color.parseColor("#9ACCCD")),
                ColorDrawable(Color.parseColor("#8FD8A0")),
                ColorDrawable(Color.parseColor("#CBD890")),
                ColorDrawable(Color.parseColor("#DACC8F")),
                ColorDrawable(Color.parseColor("#D9A790")),
                ColorDrawable(Color.parseColor("#D18FD9")),
                ColorDrawable(Color.parseColor("#FF6772")),
                ColorDrawable(Color.parseColor("#DDFB5C"))
        )
    }
}