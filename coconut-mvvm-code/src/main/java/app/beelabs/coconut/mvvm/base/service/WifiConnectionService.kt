package app.beelabs.coconut.mvvm.base.service

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager

open class WifiConnectionService {

    private var wifiManager: WifiManager? = null
    private var connectivityManager: ConnectivityManager? = null
    private var instance: WifiConnectionService? = null

    fun initializeConnection(context: Context) {
        wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @SuppressLint("MissingPermission", "NewApi")
    fun isConnected(): Boolean {
        val cap = connectivityManager!!.getNetworkCapabilities(connectivityManager!!.activeNetwork)
        if (cap != null) {
            if (cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return true else if (cap.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI)
            ) return true else if (cap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) return true
        }
        return false
    }
}