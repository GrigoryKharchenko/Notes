package com.example.examplenotes.extension

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.setStatusBarColor(@ColorRes intRes: Int) {
    activity?.window?.statusBarColor = requireContext().getCompatColor(intRes)
}

private fun Context.getCompatColor(@ColorRes intRes: Int) = ContextCompat.getColor(this, intRes)
