package com.rateabench.rateabench

import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics


class Utility {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }


        fun convertDpToPixel(context: Context, dp: Float): Float {
            return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }

        fun convertPixelsToDp(context: Context, px: Float): Float {
            return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }


    }
}