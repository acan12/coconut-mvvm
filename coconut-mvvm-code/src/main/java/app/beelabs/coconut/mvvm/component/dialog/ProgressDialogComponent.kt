package app.beelabs.coconut.mvvm.component.dialog

import android.app.ProgressDialog
import android.content.Context
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.coconut.mvvm.base.BaseConfig

object ProgressDialogComponent {

    private var dialog: ProgressDialog? = null

    fun getDialog(): ProgressDialog? = dialog

    fun showProgressDialog(context: Context, message: String, isCanceledOnTouch: Boolean) {
        if (dialog == null) {
            var text = if (message.isNotEmpty()) message else BaseConfig.DEFAULT_LOADING

            dialog = ProgressDialog(context)
            dialog?.apply {
                setMessage("$message...");
                setCanceledOnTouchOutside(isCanceledOnTouch);
                show()
            }

        }
    }

    fun dismissProgressDialog(activity: BaseActivity) {
        if (dialog == null) return
        if (activity == null || !activity.isFinishing) {
            dialog?.dismiss()
            dialog = null
        }
    }
}