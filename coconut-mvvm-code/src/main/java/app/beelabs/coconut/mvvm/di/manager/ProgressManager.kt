package app.beelabs.coconut.mvvm.di.manager

import android.content.Context
import app.beelabs.coconut.mvvm.component.dialog.ProgressDialogComponent
import app.beelabs.coconut.mvvm.di.IProgress

class ProgressManager : IProgress {

    override fun showProgressDialog(
        context: Context?,
        message: String?,
        isCanceledOnTouch: Boolean,
    ) {
        ProgressDialogComponent
    }
}