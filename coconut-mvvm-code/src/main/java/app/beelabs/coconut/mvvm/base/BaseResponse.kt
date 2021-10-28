package app.beelabs.coconut.mvvm.base

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
open class BaseResponse {
    val meta: BaseMeta? = null
    val data: BaseData? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class BaseMeta(
        var status: Boolean = false,
        var code: Int = 0,
        var message: String = ""
    ) : BaseResponse()

    @JsonIgnoreProperties(ignoreUnknown = true)
    object BaseData : Any()
}