package app.beelabs.com.coconut_mvvm.sample.model.api

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getSource() =
        apiService.callApiRXSourcesCallback()
}