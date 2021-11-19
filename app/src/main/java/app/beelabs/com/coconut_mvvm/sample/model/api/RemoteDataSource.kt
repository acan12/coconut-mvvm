package app.beelabs.com.coconut_mvvm.sample.model.api

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: Api) {
    fun getSourceByRX() =
        api.getNetwork().callApiRXSources(api.initHeader())

    suspend fun getSourceByCallback() =
        api.getNetwork().callApiSourcesCallback()
}