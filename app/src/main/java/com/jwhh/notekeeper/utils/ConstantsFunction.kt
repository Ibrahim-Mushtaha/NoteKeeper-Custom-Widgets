package com.jwhh.notekeeper.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import kotlinx.android.synthetic.main.content_items.*


interface OnListItemViewClickListener<T> {
    fun onClickItem(itemViewModel: T, type: ClickEnum,position:Int)
}


fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}
