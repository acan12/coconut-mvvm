package app.beelabs.com.coconut_mvvm.sample.model.repository

//import okhttp3.Dispatcher
import android.app.Application
import android.content.Context
import app.beelabs.coconut.mvvm.base.BaseRepository
import app.beelabs.com.coconut_mvvm.sample.model.api.RemoteDataSource
import app.beelabs.com.coconut_mvvm.sample.model.api.response.LocationResponse
import app.beelabs.com.coconut_mvvm.sample.model.dao.LocationDao
import app.beelabs.com.coconut_mvvm.sample.model.pojo.LocationEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val remoteData: RemoteDataSource,
    private val locationDao: LocationDao
) : BaseRepository() {

    fun getSourceDataRemoteRX(): Observable<LocationResponse?>? =
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

    suspend fun getLocationCaroutine() = safeApiCall { remoteData.getSourceByCallback() }


    // Database
    fun getLocalLocation(application: Application): Flow<List<LocationEntity>> =
        flow {
            emit(locationDao.getLocations())
        }.flowOn(IO)


    suspend fun insertLocalLocation(location: LocationEntity, context: Context) {
        locationDao.insertLocation(location)
    }
}