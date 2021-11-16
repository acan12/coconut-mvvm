package app.beelabs.com.coconut_mvvm.sample.model.api

import app.beelabs.coconut.mvvm.base.BaseApi
import app.beelabs.coconut.mvvm.base.BaseConfig
import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import app.beelabs.com.coconut_mvvm.sample.BuildConfig
import okhttp3.Interceptor
import javax.inject.Inject

class Api @Inject constructor(private var apiService: IApiService) : BaseApi() {

    fun initHeader(): Map<String, String> {
        var map = HashMap<String, String>()
        map.put(
            "Token",
            "eyJhbGciOiJIUzUxMiJ9.eyJkYXRhIjoxLCJlbWFpbCI6InJhbmlhQGNsYXBwaW5nYXBlLmNvbSIsInBsYXRmb3JtIjoid2Vic2l0ZSIsImtleSI6IjdYdk0xYmJuc3I3V0VjbU9ubFNjTnpvcElnMm5MQStjbld5SDc0Z1oiLCJ0aW1lc3RhbXAiOiIyMDE4LTExLTA2IDExOjI0OjQwICswNzAwIn0.wSPAcZJV8VBUSG8DAp_laovF7dFDhLxVJGQZmmDs3PsEz6SBn7FE2qF7k1UoY5Qq30wqjTDZAho1a55Yy2Fctg"
        )
        map.put("Platform", "website")
        map.put("Cache-Control", "no-store")
        map.put("Content-Type", "application/json")
        return map
    }

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

    fun getSourceNetwork(): ApiService =
        setupApiDomain(
            IConfig.API_BASE_URL,
            true,
            ApiService::class.java,
            BaseConfig.TIMEOUT_LONG_INSECOND,
            BuildConfig.DEBUG,
            arrayOf(),
            arrayOf()
        ) as ApiService


}