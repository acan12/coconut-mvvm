package app.beelabs.com.coconut_mvvm.sample.ui.activity

import android.os.Bundle
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.com.coconut_mvvm.sample.R


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}