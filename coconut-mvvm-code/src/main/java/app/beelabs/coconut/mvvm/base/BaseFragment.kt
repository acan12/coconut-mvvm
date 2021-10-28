package app.beelabs.coconut.mvvm.base

import androidx.fragment.app.Fragment
import app.beelabs.coconut.mvvm.base.interfaces.IView
import app.beelabs.coconut.mvvm.base.response.ErrorResponse

class BaseFragment : Fragment(), IView {
    override val currentActivity: BaseActivity
        get() = activity as BaseActivity

    override fun handleSuccess(response: BaseResponse?) {}
    override fun handleError(message: String?) {}
    override fun handleError(response: ErrorResponse?) {}
    override fun handleRetryConnection() {}
}