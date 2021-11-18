//package app.beelabs.coconut.mvvm.base.response
//
//import app.beelabs.coconut.mvvm.base.BaseResponse
//import app.beelabs.coconut.mvvm.base.NetworkResult
//import com.fasterxml.jackson.databind.ObjectMapper
//import retrofit2.HttpException
//
//class ErrorUtil {
//
//    inline fun <reified T> parseErrorThrowable(jsonErrorBody: String): T?? {
//        try {
////            val error: HttpException = (e as HttpException)
//            var message = "Error network connection"
//            if (!jsonErrorBody.isNullOrBlank()) {
////            var jsonResponse =
////                "{\"meta\":{\"status\":false,\"code\":${error.code()},\"message\":\"${message}\"},\"data\":null}"
//
//                var objMapper = ObjectMapper().readValue(jsonErrorBody, T::class.java)
////            error.response()?.let { response ->
////                response.errorBody()?.let { responseBody ->
////                    jsonResponse = responseBody.string()
////                    objMapper = ObjectMapper().readValue(jsonResponse, T::class.java)
////                }
////            }
//                return objMapper
//            } else return null
//        } catch (e: Exception) {
//            return null
//        }
//    }
//}