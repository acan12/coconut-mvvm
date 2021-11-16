package app.beelabs.com.coconut_mvvm.sample.viewmodel

import android.widget.Toast
import app.beelabs.coconut.mvvm.base.BaseViewModel
import app.beelabs.coconut.mvvm.base.interfaces.IView
import app.beelabs.coconut.mvvm.support.rx.RxObserver
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import app.beelabs.com.coconut_mvvm.sample.model.repository.SourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SourceRepository
) : BaseViewModel() {

    fun getSource(iview: IView) {
        repository.getSourceDataRemote()
            ?.subscribe(object : RxObserver<SourceResponse>(iview){
                 override fun onNext(o: Any) {
                    super.onNext(o)
                    Toast.makeText(iview.currentActivity, "OnNext", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    Toast.makeText(iview.currentActivity, "OnError: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getSourceLiveData(){
        repository.getSourceLiveData()
    }
}