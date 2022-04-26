package app.beelabs.com.coconut_mvvm.sample.model.api

import app.beelabs.com.coconut_mvvm.sample.model.api.response.LocationResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.HeaderMap


interface ApiService {

    @GET("provinces/")
    fun callApiRXSources(@HeaderMap header: Map<String, String>): Observable<LocationResponse?>?

    @GET("provinces/")
    suspend fun callApiSourcesCallback(@HeaderMap header: Map<String, String>): LocationResponse
}