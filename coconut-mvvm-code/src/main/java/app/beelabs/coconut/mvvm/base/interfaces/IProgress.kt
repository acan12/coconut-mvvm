package app.beelabs.coconut.mvvm.base.interfaces

import android.content.Context

interface IProgress {

    fun showProgressDialog(context: Context?, message: String?, isCanceledOnTouch: Boolean)

//    fun showSpinLoadingDialog(dialog: SpinKitLoadingDialogComponent?)
}