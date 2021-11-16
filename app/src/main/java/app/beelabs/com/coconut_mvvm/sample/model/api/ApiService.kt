package app.beelabs.com.coconut_mvvm.sample.model.api

import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("sources")
    fun callApiRXSources(@Query("language") language: String?): Observable<SourceResponse?>?
}