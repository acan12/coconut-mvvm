package app.beelabs.coconut.mvvm.base

import android.content.ComponentCallbacks2
import android.os.Build
import android.util.Log
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import androidx.appcompat.app.AppCompatActivity
import app.beelabs.coconut.mvvm.base.interfaces.IView
import app.beelabs.coconut.mvvm.base.response.ErrorResponse
//import app.beelabs.coconut.mvvm.support.util.NetworkMonitorUtil
import io.reactivex.disposables.Disposable


open class BaseActivity : AppCompatActivity(), IView,
    ComponentCallbacks2 {

    protected var disposable: Disposable? = null
    override val currentActivity: BaseActivity
        get() = this

    override fun handleSuccess(response: BaseResponse?) {}
    override fun handleError(message: String?) {}
    override fun handleError(response: ErrorResponse?) {}
    override fun handleRetryConnection() {}

//    fun setupNetworkMonitoring(networkMonitor: NetworkMonitorUtil) {
//        networkMonitor.result = { isAvailable, type ->
//            runOnUiThread {
//                when (isAvailable) {
//                    true -> {
//                        when (type) {
//                            NetworkMonitorUtil.ConnectionType.Wifi -> {
//                                Log.i("NETWORK_MONITOR_STATUS", "Wifi Connection")
//                            }
//                            NetworkMonitorUtil.ConnectionType.Cellular -> {
//                                Log.i("NETWORK_MONITOR_STATUS", "Cellular Connection")
//                            }
//                            else -> {}
//                        }
//                    }
//                    false -> {
//                        Log.i("NETWORK_MONITOR_STATUS", "No Connection")
//                    }
//                }
//            }
//        }
//    }

    fun setupStatusBarStyle(statusBarColor: Int, lightOn: Boolean, activity: BaseActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (lightOn) {
                activity.window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            if (statusBarColor != 0) activity.window.statusBarColor = statusBarColor
        }
    }
}