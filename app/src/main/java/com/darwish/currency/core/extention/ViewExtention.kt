package com.darwish.currency.core.extention

import android.view.View

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInVisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.manageVisibility(visible: Boolean) {
    visibility = if (visible)
        View.VISIBLE
    else
        View.GONE
}