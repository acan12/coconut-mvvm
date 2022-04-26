package app.beelabs.coconut.mvvm.base

import app.beelabs.coconut.mvvm.base.exception.NetworkLostConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.HttpException

open class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            Resource.Loading<T>()
            try {
                Resource.Success(apiCall.invoke())

            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Error(
                            throwable,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    is NetworkLostConnectionException -> {
                        Resource.Error(
                            throwable = throwable,
                            503
                        )
                    }
                    else -> {
                        Resource.Error(
                            throwable = throwable
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
        val throwable: Throwable,
        val errorCode: Int? = null,
        val errorBody: ResponseBody? = null
    ) : Resource<Nothing>()

    class Loading<out T>() : Resource<T>()
}