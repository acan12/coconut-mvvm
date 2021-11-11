package app.beelabs.coconut.mvvm.base

import app.beelabs.coconut.mvvm.base.helper.UnsafeHttpClientHelper
import app.beelabs.coconut.mvvm.component.interceptor.ConnectivityInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

open class BaseManager {

    enum class Method {
        PUT, POST, UPDATE
    }

    protected fun getHttpClient(
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

        // default interceptor
        customInterceptors[0] = ConnectivityInterceptor()

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
        return httpClient.build();
    }

}