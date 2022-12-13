package com.example.sprint_marvel_serasa.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.sprint_marvel_serasa.R

fun FragmentActivity.replaceView(
    fragment: Fragment,
    hideFragment: Fragment? = null,
    @IdRes containerId: Int = R.id.container,
    addToPile: Boolean = false
) {
    if (addToPile) {
        supportFragmentManager.beginTransaction()
            .add(containerId, fragment)
            .hide(hideFragment!!)
            .addToBackStack(null)
            .commit()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }
}

fun FragmentActivity.showFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .show(fragment)
}

fun FragmentActivity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

private fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun checkInternetConnection(context: Context?): Boolean {
    val connectivityManager =
         context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

enum class ErrorString(val url: String){
    ERROR_CARD_IMAGE("https://i.pinimg.com/originals/f7/5b/49/f75b49ff9d03bba7264d4aa651613192.jpg"),
    ERROR_URL_GIF("http://i.annihil.us/u/prod/marvel/i/mg/f/60/4c002e0305708.gif"),
    ERROR_URL_JPG("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
}