package app.beelabs.com.coconut_mvvm.sample.ui.interfaces

import app.beelabs.coconut.mvvm.base.interfaces.IView
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse

interface IMainView : IView {

    fun handleSourceResponseData(data: SourceResponse)
}