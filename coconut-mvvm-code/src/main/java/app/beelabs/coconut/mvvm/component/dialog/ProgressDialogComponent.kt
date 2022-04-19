package app.beelabs.coconut.mvvm.component.dialog

import android.annotation.SuppressLint
import android.content.Context
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.coconut.mvvm.base.BaseConfig
import app.beelabs.coconut.mvvm.support.ui.CoconutProgressDialog
import app.beelabs.coconut.mvvm.support.ui.CoconutProgressDialog.Companion.THEME_DARK
import leakcanary.AppWatcher

open class ProgressDialogComponent {

    companion object {
        private var dialog: CoconutProgressDialog? = null

        @SuppressLint("NewApi")
        fun showProgressDialog(context: Context, message: String, isCanceledOnTouch: Boolean) {
            if (dialog == null) {
                var text = if (message.isNotEmpty()) message else BaseConfig.DEFAULT_TEXT_LOADING

                dialog = CoconutProgressDialog(context)
                dialog!!.setTheme(THEME_DARK)
                dialog?.apply {
                    setMessage("$text...");
                    setCancelable(isCanceledOnTouch);
                    show()
                }
            }

            AppWatcher.objectWatcher.watch(dialog!!, "View was detached")
        }

        fun dismissProgressDialog(activity: BaseActivity) {
            if (dialog == null) return
            if (activity == null || !activity.isFinishing) {
                dialog?.dismiss()
                dialog = null
            }
        }
    }
}