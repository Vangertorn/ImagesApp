package com.vangertorn.imagesapp.util.extension

import android.view.View
import android.view.ViewGroup

fun View.goneUnless(predicate: Boolean) = if (predicate) this.visible() else this.gone()

fun View.invisibleUnless(predicate: Boolean) = if (predicate) this.visible() else this.invisible()

fun <T : View> T.visible() = this.apply { this.visibility = View.VISIBLE }

fun <T : View> T.invisible() = this.apply { this.visibility = View.INVISIBLE }

fun <T : View> T.gone() = this.apply { this.visibility = View.GONE }

fun View.setVerticalMargin(marginTop: Int = 0, marginBottom: Int = 0) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(
        layoutParams.leftMargin,
        marginTop,
        layoutParams.rightMargin,
        marginBottom
    )
    this.layoutParams = layoutParams
}
