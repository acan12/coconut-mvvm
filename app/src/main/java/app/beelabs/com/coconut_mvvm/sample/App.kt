package app.beelabs.com.coconut_mvvm.sample

import app.beelabs.coconut.mvvm.base.BaseApp
import app.beelabs.coconut.mvvm.base.service.WifiConnectionService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
//        dispatchNetworkConnectivity(this)
        WifiConnectionService.getInstance().setupConnection(applicationContext)
    }


}