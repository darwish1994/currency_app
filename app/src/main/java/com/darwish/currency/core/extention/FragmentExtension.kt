package com.darwish.currency.core.extention

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


fun Fragment.showToast(msg:String)= Toast.makeText(requireActivity(),msg,Toast.LENGTH_SHORT).show()

fun Fragment.showToast(@StringRes msg:Int)= Toast.makeText(requireActivity(),msg,Toast.LENGTH_SHORT).show()
