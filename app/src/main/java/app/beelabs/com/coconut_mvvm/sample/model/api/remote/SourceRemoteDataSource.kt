package app.beelabs.com.coconut_mvvm.sample.model.api.remote

import app.beelabs.com.coconut_mvvm.sample.model.api.Api
import javax.inject.Inject

class SourceRemoteDataSource @Inject constructor(private val api: Api) {
    fun getSourceByRX() =
        api.getDomainNetwork().callApiRXSources(api.initHeader())

    suspend fun getSourceByCallback() =
        api.getDomainNetwork().callApiSourcesCallback(api.initHeader())
}