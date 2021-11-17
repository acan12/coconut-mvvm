package app.beelabs.com.coconut_mvvm.sample.model.repository

import android.util.Log
import app.beelabs.coconut.mvvm.base.BaseRepository
import app.beelabs.com.coconut_mvvm.sample.model.api.Api
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import javax.inject.Inject

class SourceRepository @Inject constructor(
    private val api: Api
) : BaseRepository() {

    fun getSourceDataRemote(): Observable<SourceResponse?>? =
        api.getSourceNetwork().callApiRXSources(api.initHeader())?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())

    fun getSourceLiveData() {
        api.getSourceNetwork()
            .callApiRXSourcesCallback()
            ?.enqueue(object : Callback<SourceResponse?> {
                override fun onResponse(
                    call: Call<SourceResponse?>,
                    response: Response<SourceResponse?>
                ) {
                    Log.d("TESTING", "Ok")
                }

                override fun onFailure(call: Call<SourceResponse?>, t: Throwable) {
                    Log.d("TESTING", "Failed")
                }

            })
    }
}