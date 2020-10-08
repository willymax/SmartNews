package com.william.smartnews.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.william.smartnews.R

/**
 * Created by Willy on 08/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class Tools {
    companion object {
        fun displayImageOriginal(
            ctx: Context,
            img: ImageView,
            url: String?
        ) {
            try {
                Glide.with(ctx).load(url)
                    .placeholder(R.drawable.news_placeholder).error(R.drawable.news_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img)
            } catch (e: Exception) {
            }
        }
    }
}