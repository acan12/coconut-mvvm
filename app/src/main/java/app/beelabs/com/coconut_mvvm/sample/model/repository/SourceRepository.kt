package app.beelabs.com.coconut_mvvm.sample.model.repository

import app.beelabs.coconut.mvvm.base.BaseRepository
import app.beelabs.coconut.mvvm.base.Resource
import app.beelabs.com.coconut_mvvm.sample.model.api.Api
import app.beelabs.com.coconut_mvvm.sample.model.api.RemoteDataSource
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SourceRepository @Inject constructor(
    private val remoteData: RemoteDataSource
) : BaseRepository() {

    fun getSourceDataRemoteRX(): Observable<SourceResponse?>? =
        remoteData.getSourceByRX()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())

//    fun getSourceCallback() {
//        api.getSourceNetwork()
//            .callApiRXSourcesCallback()
//            ?.enqueue(object : Callback<SourceResponse?> {
//                override fun onResponse(
//                    call: Call<SourceResponse?>,
//                    response: Response<SourceResponse?>
//                ) {
//                    Log.d("TESTING", "Ok")
//                }
//
//                override fun onFailure(call: Call<SourceResponse?>, t: Throwable) {
//                    Log.d("TESTING", "Failed")
//                }
//
//            })
//    }

    suspend fun getSourceCaroutine() = safeApiCall { remoteData.getSourceByCallback() }




}