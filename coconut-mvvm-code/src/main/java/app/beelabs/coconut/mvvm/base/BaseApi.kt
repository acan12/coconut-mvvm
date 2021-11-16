package app.beelabs.coconut.mvvm.base

import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import okhttp3.Interceptor
import javax.inject.Inject

open class BaseApi {

    @Inject
    lateinit var apiService: IApiService

    protected fun setupApiDomain(
        apiDomain: String,
        allowUntrusted: Boolean,
        apiservice: Class<*>,
        timeoutLong: Long,
        enableLogging: Boolean,
        interceptors: Array<Interceptor>,
        networkInterceptors: Array<Interceptor>
    ): Any {
        return apiService.initApiService(
            apiDomain,
            allowUntrusted,
            apiservice,
            timeoutLong,
            enableLogging,
            interceptors,
            networkInterceptors
        )
    }

}