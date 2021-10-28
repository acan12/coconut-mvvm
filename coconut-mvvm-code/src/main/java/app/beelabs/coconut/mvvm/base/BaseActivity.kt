package app.beelabs.coconut.mvvm.base

import android.content.ComponentCallbacks2
import android.os.Build
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import androidx.appcompat.app.AppCompatActivity
import app.beelabs.coconut.mvvm.base.interfaces.IView
import app.beelabs.coconut.mvvm.base.response.ErrorResponse

open class BaseActivity : AppCompatActivity(), IView,
    ComponentCallbacks2 {
    override val currentActivity: BaseActivity
        get() = this

    override fun handleSuccess(response: BaseResponse?) {}
    override fun handleError(message: String?) {}
    override fun handleError(response: ErrorResponse?) {}
    override fun handleRetryConnection() {}

    fun setupStatusBarStyle(statusBarColor: Int, lightOn: Boolean, activity: BaseActivity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(lightOn){
                activity.window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            if(statusBarColor != 0 ) activity.window.statusBarColor = statusBarColor
        }
    }
}