package app.beelabs.com.coconut_mvvm.sample.model.api

import app.beelabs.coconut.mvvm.base.BaseApi
import app.beelabs.coconut.mvvm.base.BaseConfig
import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import app.beelabs.com.coconut_mvvm.sample.BuildConfig
import javax.inject.Inject

open class Api @Inject constructor(val apiService: IApiService) : BaseApi() {

    fun initHeader(): Map<String, String> {
        var map = HashMap<String, String>()
        map.put("Cache-Control", "no-store")
        map.put("Content-Type", "application/json")
        return map
    }

    fun getDomainNetwork(): ApiService =
        setupApiDomain(
            apiDomain = IConfig.API_BASE_URL,
            allowUntrusted = true,
            apiservice = ApiService::class.java,
            timeOut = BaseConfig.TIMEOUT_LONG_INSECOND,
            enableLogging = BuildConfig.DEBUG,
            apiService = apiService
        ) as ApiService
}