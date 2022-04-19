package app.beelabs.coconut.mvvm.base

import android.content.Context
import androidx.fragment.app.Fragment
import app.beelabs.coconut.mvvm.base.interfaces.IView
import app.beelabs.coconut.mvvm.base.response.ErrorResponse
import app.beelabs.coconut.mvvm.support.util.NetworkMonitorUtil
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment(), IView {
    override val currentActivity: BaseActivity
        get() = activity as BaseActivity

    override fun handleSuccess(response: BaseResponse?) {}
    override fun handleError(message: String?) {}
    override fun handleError(response: ErrorResponse?) {}
    override fun handleRetryConnection() {}

}