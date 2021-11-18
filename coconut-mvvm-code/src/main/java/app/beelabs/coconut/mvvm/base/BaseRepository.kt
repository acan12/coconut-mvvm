package app.beelabs.coconut.mvvm.base

import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.Response

open class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }

            val errorResponse =
                ObjectMapper().readValue(response.errorBody().toString(), BaseResponse::class.java)
            return NetworkResult.Error(errorResponse)
//            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return NetworkResult.Error(null)
//            return error(e.message ?: e.toString())
        }
    }
//    private fun <T> error(errorMessage: String): NetworkResult<T> =
//        NetworkResult.Error("Api call failed $errorMessage")

}

sealed class NetworkResult<T>(
    val data: T? = null,
    val error: BaseResponse? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(error: BaseResponse?) : NetworkResult<T>(null, error)
    class Loading<T> : NetworkResult<T>()
}