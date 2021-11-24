package app.beelabs.coconut.mvvm.di.manager

import android.content.Context
import app.beelabs.coconut.mvvm.base.BaseManager
import app.beelabs.coconut.mvvm.base.interfaces.IProgress
import app.beelabs.coconut.mvvm.component.dialog.ProgressDialogComponent

class ProgressManager : BaseManager(), IProgress {

    override fun showProgressDialog(
        context: Context?,
        message: String?,
        isCanceledOnTouch: Boolean,
    ) {
        ProgressDialogComponent()
    }
}