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



}