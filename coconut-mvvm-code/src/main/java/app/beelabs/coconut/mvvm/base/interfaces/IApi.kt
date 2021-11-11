package app.beelabs.coconut.mvvm.base.interfaces

import okhttp3.Interceptor

interface IApi {
    fun initApiService(
        piDomain: String,
        allowUntrusted: Boolean,
        clazz: Class<IApiService>,
        timeout: Long,
        enableLoggingHttp: Boolean,
        interceptors: Array<Interceptor>,
        networkInterceptors: Array<Interceptor>): Any
}