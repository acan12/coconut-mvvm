package app.beelabs.coconut.mvvm.di.interceptor

import app.beelabs.coconut.mvvm.base.exception.LostNetworkConnectionException
import app.beelabs.coconut.mvvm.base.service.WifiConnectionService
import okhttp3.Interceptor
import okhttp3.Response

class WifiConnectionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if(WifiConnectionService.getInstance().isConnected()){
            throw LostNetworkConnectionException()
        } else {
            return chain.proceed(chain.request())
        }
    }
}