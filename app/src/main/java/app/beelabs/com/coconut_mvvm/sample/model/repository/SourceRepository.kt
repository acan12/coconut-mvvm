package app.beelabs.com.coconut_mvvm.sample.model.repository

import app.beelabs.coconut.mvvm.base.BaseRepository
import app.beelabs.com.coconut_mvvm.sample.model.api.Api
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SourceRepository @Inject constructor(
    val api: Api
) : BaseRepository() {

    fun getSourceDataRemote() {
        api.getSourceNetwork().callApiRXSources("en")?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }
}