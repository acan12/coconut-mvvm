package app.beelabs.com.coconut_mvvm.sample.model.api.response

import app.beelabs.coconut.mvvm.base.BaseResponse
import app.beelabs.com.coconut_mvvm.sample.model.pojo.Source
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class SourceResponse: BaseResponse() {
    var sources : List<Source>? = null
}
