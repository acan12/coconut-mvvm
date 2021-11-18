package app.beelabs.com.coconut_mvvm.sample.model.repository

import androidx.lifecycle.asLiveData
import app.beelabs.coconut.mvvm.base.BaseRepository
import app.beelabs.coconut.mvvm.base.BaseResponse
import app.beelabs.coconut.mvvm.base.NetworkResult
import app.beelabs.com.coconut_mvvm.sample.model.api.Api
import app.beelabs.com.coconut_mvvm.sample.model.api.RemoteDataSource
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class SourceRepository @Inject constructor(
    private val api: Api
) : BaseRepository() {

    fun getSourceDataRemote(): Observable<SourceResponse?>? =
        api.getSourceNetwork().callApiRXSources(api.initHeader())?.subscribeOn(Schedulers.io())
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

    suspend fun getSourceCaroutine() = api.getSourceNetwork().callApiRXSourcesCallback()


}