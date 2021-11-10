package app.beelabs.coconut.mvvm.di

import android.content.Context

interface IProgress {

    fun showProgressDialog(context: Context?, message: String?, isCanceledOnTouch: Boolean)

//    fun showSpinLoadingDialog(dialog: SpinKitLoadingDialogComponent?)
}