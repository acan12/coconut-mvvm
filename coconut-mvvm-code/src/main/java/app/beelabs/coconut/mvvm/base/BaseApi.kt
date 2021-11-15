package app.beelabs.coconut.mvvm.base

import app.beelabs.coconut.mvvm.base.interfaces.IApi
import okhttp3.Interceptor
import javax.inject.Inject
import kotlin.reflect.KClass

open class BaseApi {

    @Inject
    lateinit var api: IApi

    protected fun setupApiDomain(
        apiDomain: String,
        allowUntrusted: Boolean,
        apiservice: Class<*>,
        timeoutLong: Long,
        enableLogging: Boolean,
        interceptors: Array<Interceptor>,
        networkInterceptors: Array<Interceptor>
    ): IApi {
        return api.initApiService(
            apiDomain,
            allowUntrusted,
            apiservice,
            timeoutLong,
            enableLogging,
            interceptors,
            networkInterceptors
        ) as IApi
    }

}