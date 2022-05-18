package com.vangertorn.imagesapp.util.extension

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.vangertorn.imagesapp.R

fun ImageView.setCustomDrawable(isShowFavorite: Boolean) {
    val drawable = if (isShowFavorite) {
        AppCompatResources.getDrawable(context, R.drawable.ic_favorite_active)
    } else {
        AppCompatResources.getDrawable(context, R.drawable.ic_favorite_inactive)
    }
    setImageDrawable(drawable)
}