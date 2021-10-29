package app.beelabs.com.coconut_mvvm.ui.activity

import android.os.Bundle
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.com.coconut_mvvm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}