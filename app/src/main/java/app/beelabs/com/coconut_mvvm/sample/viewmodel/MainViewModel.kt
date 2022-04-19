package app.beelabs.com.coconut_mvvm.sample.viewmodel

import android.widget.Toast
import app.beelabs.coconut.mvvm.base.BaseViewModel
import app.beelabs.coconut.mvvm.base.interfaces.IView
import app.beelabs.coconut.mvvm.support.rx.RxObserver
import app.beelabs.com.coconut_mvvm.sample.model.api.response.LocationResponse
import app.beelabs.com.coconut_mvvm.sample.model.repository.LocationRepository
import app.beelabs.com.coconut_mvvm.sample.ui.interfaces.IMainView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: LocationRepository
) : BaseViewModel() {
    var disposable: Disposable? = null

    fun getSource(iview: IView) {
        repository.getSourceDataRemoteRX()
            ?.subscribe(object : RxObserver<LocationResponse>(iview, "Memuat") {
                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    disposable = d
                }

                override fun onNext(o: Any) {
                    super.onNext(o)
                    val data = o as LocationResponse
                    when (iview) {
                        is IMainView -> iview.handleSourceResponseData(data)
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    Toast.makeText(
                        iview.currentActivity,
                        "OnError: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}