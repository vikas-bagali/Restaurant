package com.demo.restaurant.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import java.lang.Exception

class Helper {

    companion object {

        fun dismissKeyboard(mActivity: Activity?) {
            if (mActivity != null) {
                val imm =
                    mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (null != mActivity.currentFocus) imm.hideSoftInputFromWindow(
                    mActivity.currentFocus!!
                        .applicationWindowToken, 0
                )
            }
        }

        fun isKeyboardVisible(mActivity: Activity): Boolean {
            return try {
                val imm =
                    mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.isActive
            } catch (e: Exception) {
                false
            }
        }
    }
}