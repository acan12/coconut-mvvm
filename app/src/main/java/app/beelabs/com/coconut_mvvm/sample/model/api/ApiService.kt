package app.beelabs.com.coconut_mvvm.sample.model.api

import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query


interface ApiService {

    @GET("provinces")
    fun callApiRXSources(@HeaderMap header: Map<String, String>): Observable<SourceResponse?>?

    @GET("provinces")
    suspend fun callApiRXSourcesCallback(): Response<SourceResponse>
}