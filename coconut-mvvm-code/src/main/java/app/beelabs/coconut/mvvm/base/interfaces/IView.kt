package app.beelabs.coconut.mvvm.base.interfaces

import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.coconut.mvvm.base.BaseResponse




interface IView {
    val currentActivity: BaseActivity

    fun handleSuccess(response: BaseResponse?)
    fun handleFail(response: BaseResponse?)
    fun handleFail(message: String?)

    fun handleReplyConnection()
}