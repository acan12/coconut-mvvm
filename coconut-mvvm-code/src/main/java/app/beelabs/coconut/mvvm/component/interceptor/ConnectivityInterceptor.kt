package app.beelabs.coconut.mvvm.component.interceptor

import app.beelabs.coconut.mvvm.base.exception.NoConnectivityException
import app.beelabs.coconut.mvvm.base.service.WifiConnectionService
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!WifiConnectionService().isConnected()) {
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }

}