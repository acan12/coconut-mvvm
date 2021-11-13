package app.beelabs.coconut.mvvm.base

import app.beelabs.coconut.mvvm.base.interfaces.IApi
import javax.inject.Inject

class BaseRepository @Inject constructor(
    private val apiService: IApi
) {

    fun getApiService(): IApi = apiService
}