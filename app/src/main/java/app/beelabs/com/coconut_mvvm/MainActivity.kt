package app.beelabs.com.coconut_mvvm

import android.os.Bundle
import app.beelabs.coconut.mvvm.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}