package app.beelabs.coconut.mvvm.support.dialog

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import app.beelabs.coconut.mvvm.R
import app.beelabs.coconut.mvvm.base.BaseDialog
import app.beelabs.coconut.mvvm.base.interfaces.IView

class CoconutAlertNoConnectionDialog(val iview: IView) :
    BaseDialog(iview as Activity, R.style.CoconutDialogFullScreen) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowContentDialogLayout(R.layout.dialog_coconut_alert_no_connection)
        val btnReconnect = findViewById<Button>(R.id.coconut_btn_reconnect)
        btnReconnect.setOnClickListener {
            dismiss()
            iview.callbackReConnectingNetwork()
        }
    }
}