package app.beelabs.com.coconut_mvvm.sample.model.api

import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("provinces")
    fun callApiRXSources(): Observable<SourceResponse?>?

    @GET("provinces")
    fun callApiRXSourcesLiveData(): Call<SourceResponse?>?
}