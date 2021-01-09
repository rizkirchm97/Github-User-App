package com.rizki.githubuserapp.util

import android.graphics.Typeface
import android.view.View
import android.widget.SearchView
import android.widget.TextView

fun SearchView.setTypeFace(typeface: Typeface?) {
    val idT = context.resources.getIdentifier("search_src_text", "id", "android")
    val searchText = rootView.findViewById<View>(idT) as TextView
    searchText.textSize = 15F
    searchText.typeface = typeface
}

fun View.isVisible(){
    visibility = View.VISIBLE
}

fun View.isGone() {
    visibility = View.GONE
}

fun View.isInvisible() {
    visibility = View.INVISIBLE
}
