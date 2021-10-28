package app.beelabs.coconut.mvvm.base.response

import app.beelabs.coconut.mvvm.base.BaseResponse
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.HttpException

class ErrorResponse : BaseResponse() {

    inline fun <reified T> parseErrorThrowable(e: Throwable): T? {
        try {
            val error: HttpException = (e as HttpException)
            var message = "Error network connection"
            if (!error.message().isNullOrBlank()) message = error.message()
            var jsonResponse =
                "{\"meta\":{\"status\":false,\"code\":${error.code()},\"message\":\"${message}\"},\"data\":null}"
            var objMapper = ObjectMapper().readValue(jsonResponse, T::class.java)
            error.response()?.let { response ->
                response.errorBody()?.let { responseBody ->
                    jsonResponse = responseBody.string()
                    objMapper = ObjectMapper().readValue(jsonResponse, T::class.java)
                }
            }
            return objMapper
        } catch (e: Exception) {
            return null
        }
    }
}