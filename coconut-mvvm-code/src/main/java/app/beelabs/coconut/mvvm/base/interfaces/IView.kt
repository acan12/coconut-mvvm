package app.beelabs.coconut.mvvm.base.interfaces

import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.coconut.mvvm.base.BaseResponse
import app.beelabs.coconut.mvvm.base.response.ErrorResponse


interface IView {
    val currentActivity: BaseActivity

    fun handleSuccess(response: BaseResponse?)
    fun handleError(message: String?)
    fun handleError(response: ErrorResponse?)

    fun handleNoConnectionInternet()
    fun callbackReConnectingNetwork()
}