package app.beelabs.coconut.mvvm.base

class BaseApi {
    private lateinit var apiDomain: String
    private lateinit var baseApi: BaseApi

    fun getInstance(): BaseApi = if (baseApi == null) BaseApi() else baseApi

    fun setupApiDomain(
        domain: String,
    ): Any = ""

}