package app.beelabs.coconut.mvvm.base

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import app.beelabs.coconut.mvvm.R


class BaseDialog(context: Context, style: Int) : Dialog(context, style) {
    private val widthLayout = WindowManager.LayoutParams.MATCH_PARENT
    private val heightLayout = WindowManager.LayoutParams.MATCH_PARENT

    open fun setWindowContentDialogLayout(layoutId: Int) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window?.let { w ->
            w.setBackgroundDrawable(
                ColorDrawable(
                    context.resources.getColor(R.color.coconut_background_dialog_color)
                )
            )
            w.setLayout(widthLayout, heightLayout)
            w.attributes.apply {
                w.attributes?.gravity = Gravity.CENTER
            }
        }

        setContentView(layoutId)
    }
}