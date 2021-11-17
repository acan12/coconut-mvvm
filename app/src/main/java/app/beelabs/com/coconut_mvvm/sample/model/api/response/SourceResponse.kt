package app.beelabs.com.coconut_mvvm.sample.model.api.response

import app.beelabs.coconut.mvvm.base.BaseResponse
import app.beelabs.com.coconut_mvvm.sample.model.pojo.Source
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class SourceResponse(): BaseResponse() {

    @JsonProperty("data")
    var locationData: List<LocationData> = listOf()

    class LocationData {
        val id: String = ""
        val name: String = ""
    }

}



