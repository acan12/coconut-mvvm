package app.beelabs.coconut.mvvm.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.UnknownHostException

open class BaseRepository {

//    fun <T> resultFlow(
//        localLoad: suspend () -> Resource<T>,
//        networkCall: suspend () -> Resource<T>,
//        saveCallResult: suspend (T) -> Unit
//    ) = flow<Resource<T>> {
//        emit(Resource.Loading<T>())
//        val data = (localLoad() as Resource.Success<T>).value
//        when {
//            data != null -> checkDataType(data, networkCall, saveCallResult)
//            else -> emit(callRemote(networkCall, saveCallResult))
//        }
//    }.flowOn(Dispatchers.IO)

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            Resource.Loading<T>()
            try {
                Resource.Success(apiCall.invoke())

            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Error(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        val isNetworkError = (throwable is UnknownHostException)
                        Resource.Error(
                            isNetworkError = isNetworkError,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }
}

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()

    class Loading<out T>() : Resource<T>()
}