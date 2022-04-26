package app.beelabs.com.coconut_mvvm.sample.model.api.remote

import app.beelabs.com.coconut_mvvm.sample.model.api.Api
import javax.inject.Inject

class SourceRemoteDataSource @Inject constructor(private val api: Api) {
    fun getSourceByRX() =
        api.getNetwork().callApiRXSources(api.initHeader())

    suspend fun getSourceByCallback() =
        api.getNetwork().callApiSourcesCallback(api.initHeader())
}