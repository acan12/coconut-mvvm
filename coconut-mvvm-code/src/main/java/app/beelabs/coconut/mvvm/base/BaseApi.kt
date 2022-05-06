package app.beelabs.coconut.mvvm.base

import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import okhttp3.Interceptor

open class BaseApi() {
    protected fun setupApiDomain(
        apiService: IApiService,
        apiDomain: String,
        allowUntrusted: Boolean,
        apiservice: Class<*>,
        timeOut: Long,
        enableLogging: Boolean,
        interceptors: Array<Interceptor> = arrayOf(),
        networkInterceptors: Array<Interceptor> = arrayOf()
    ): Any {
        return apiService.initApiService(
            apiDomain,
            allowUntrusted,
            apiservice,
            timeOut,
            enableLogging,
            interceptors,
            networkInterceptors
        )
    }
}