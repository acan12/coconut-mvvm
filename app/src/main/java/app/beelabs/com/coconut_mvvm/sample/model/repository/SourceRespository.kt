package app.beelabs.com.coconut_mvvm.sample.model.repository

import app.beelabs.coconut.mvvm.base.BaseRepository
import app.beelabs.coconut.mvvm.base.interfaces.IApi
import javax.inject.Inject

class SourceRespository @Inject constructor(
    val api: IApi
) : BaseRepository() {

}