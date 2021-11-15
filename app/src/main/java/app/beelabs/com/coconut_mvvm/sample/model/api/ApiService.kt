package app.beelabs.com.coconut_mvvm.sample.model.api

import android.database.Observable
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("sources")
    fun callApiRXSources(@Query("language") language: String?): Observable<SourceResponse?>?
}