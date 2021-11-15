package app.beelabs.com.coconut_mvvm.sample.model.pojo

data class Source(
    val id: String? = "",
    val name: String? = "",
    val description: String? = "",
    val url: String? = "",
    val category: String? = "",
    val language: String? = "",
    val country: String? = "",
    val urlLogos: UrlLogos? = null
)


data class UrlLogos(
    val small: String? = "",
    val medium: String? = "",
    val large: String? = ""
)