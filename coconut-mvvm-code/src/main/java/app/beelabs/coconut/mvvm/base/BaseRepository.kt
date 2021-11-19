package app.beelabs.coconut.mvvm.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

open class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())

            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Error(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Error(true, null, null)
                    }
                }
            }
        }
    }
//    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
//        try {
//            val response = apiCall()
//            if (response.isSuccessful) {
//                val body = response.body()
//                body?.let {
//                    return Resource.Success(body)
//                }
//            }
//            return error("${response.code()} ${response.message()}")
//        } catch (e: Exception) {
//            return error(e.message ?: e.toString())
//        }
//    }
//
//    private fun <T> error(errorMessage: String): Resource<T> =
//        Resource.Error("Api call failed $errorMessage")
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