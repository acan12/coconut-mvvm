package app.beelabs.coconut.mvvm.support.rx

import app.beelabs.coconut.mvvm.base.BaseDialog
import app.beelabs.coconut.mvvm.base.BaseResponse
import app.beelabs.coconut.mvvm.base.exception.LostNetworkConnectionException
import app.beelabs.coconut.mvvm.base.interfaces.IView
import app.beelabs.coconut.mvvm.component.dialog.ProgressDialogComponent
import app.beelabs.coconut.mvvm.support.dialog.CoconutAlertNoConnectionDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class RxObserverKotlin<P : BaseResponse> : Observer<Any> {
    private var iv: IView
    private var messageLoading: String? = null
    private var timeMilis: Long = 0
    private var dialogType = 0

    interface DialogTypeEnum {
        companion object {
            const val DEFAULT = 0
            const val SPINKIT = 1
        }
    }

    constructor(iv: IView) {
        this.iv = iv
    }

    constructor(iv: IView, messageLoading: String?) {
        this.iv = iv
        this.messageLoading = messageLoading
        timeMilis = 0
    }

    constructor(iv: IView, messageLoading: String?, timeMilis: Long) {
        this.iv = iv
        this.messageLoading = messageLoading
        this.timeMilis = timeMilis
    }

    fun setDialogType(dialogType: Int): RxObserverKotlin<*> {
        this.dialogType = dialogType
        return this
    }

    override fun onSubscribe(d: Disposable) {
        ProgressDialogComponent.dismissProgressDialog(iv.currentActivity)
        //        SpinKitLoadingDialogComponent.dismissProgressDialog(iv.getCurrentActivity(), timeMilis);
        if (messageLoading != null) {
            when (dialogType) {
                DialogTypeEnum.DEFAULT -> ProgressDialogComponent.showProgressDialog(
                    iv.currentActivity,
                    messageLoading!!,
                    false
                )
            }
        }
    }

    override fun onNext(o: Any?) {
//        SpinKitLoadingDialogComponent.dismissProgressDialog(iv.getCurrentActivity(), timeMilis);
        ProgressDialogComponent.dismissProgressDialog(iv.currentActivity)
    }

    override fun onError(e: Throwable) {
        ProgressDialogComponent.dismissProgressDialog(iv.currentActivity)
        //        SpinKitLoadingDialogComponent.dismissProgressDialog(iv.getCurrentActivity(), timeMilis);
//
        if (e is LostNetworkConnectionException) {
            if (dialogNoconnection != null){
                if (dialogNoconnection!!.isShowing) dialogNoconnection!!.dismiss()
            }
            dialogNoconnection = CoconutAlertNoConnectionDialog(iv.currentActivity)
            (dialogNoconnection as CoconutAlertNoConnectionDialog).show()
            return
        }
    }

    override fun onComplete() {}

    companion object {
        private var dialogNoconnection: BaseDialog? = null
    }
}