package app.beelabs.coconut.mvvm.di.manager

import app.beelabs.coconut.mvvm.base.BaseManager
import app.beelabs.coconut.mvvm.base.interfaces.IApi
import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class ApiManager : BaseManager(), IApi {
    override fun initApiService(
        apiDomain: String,
        allowUntrusted: Boolean,
        clazz: Class<IApiService>,
        timeout: Long,
        enableLoggingHttp: Boolean,
        interceptors: Array<Interceptor>,
        networkInterceptors: Array<Interceptor>,
    ): Any {
        return Retrofit.Builder()
            .baseUrl(apiDomain)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                getHttpClient(
                    allowUntrusted,
                    timeout,
                    enableLoggingHttp,
                    interceptors,
                    networkInterceptors
                )
            ).build().create(clazz)
    }
}