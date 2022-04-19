package app.beelabs.coconut.mvvm.di.manager

import app.beelabs.coconut.mvvm.base.BaseManager
import app.beelabs.coconut.mvvm.base.helper.UnsafeHttpClientHelper
import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceManager : BaseManager(), IApiService {
    override fun initApiService(
        apiDomain: String,
        allowUntrusted: Boolean,
        clazz: Class<*>,
        timeout: Long,
        enableLoggingHttp: Boolean,
        interceptors: Array<Interceptor>,
        networkInterceptors: Array<Interceptor>
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

    private fun getHttpClient(
        allowUntrustedSSL: Boolean,
        timeout: Long,
        enableLoggingHttp: Boolean,
        customInterceptors: Array<Interceptor>,
        customNetworkInterceptors: Array<Interceptor>,
    ): OkHttpClient {
        var httpClient = OkHttpClient.Builder()

        // allowUntrustedSSL: true , if activate Untrusted SSL
        if (allowUntrustedSSL) httpClient = UnsafeHttpClientHelper.getUnsafeOkHttpClient(httpClient)
        httpClient.connectTimeout(timeout, TimeUnit.SECONDS)
        httpClient.readTimeout(timeout, TimeUnit.SECONDS)

        // Interceptor logging HTTP request
        if (enableLoggingHttp) {
            var logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)
        }

        // check network state information interceptor
//        val connectivityManager = con.getSystemService(Context.CONNECTIVITY_SERVICE)
//        httpClient.addInterceptor(ConnectivityInterceptor(connectionManager))

        // Pre-Interceptor
        if (customInterceptors.isNotEmpty()) {
            for (interceptor: Interceptor in customInterceptors) {
                httpClient.addInterceptor(interceptor)
            }
        }

        // Post-Network Interceptor
        if (customNetworkInterceptors.isNotEmpty()) {
            for (interceptor: Interceptor in customNetworkInterceptors) {
                httpClient.addNetworkInterceptor(interceptor)
            }
        }
        return httpClient.build()
    }


}