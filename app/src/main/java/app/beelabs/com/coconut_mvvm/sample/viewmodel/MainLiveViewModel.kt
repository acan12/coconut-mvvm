package app.beelabs.com.coconut_mvvm.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.beelabs.coconut.mvvm.base.BaseViewModel
import app.beelabs.coconut.mvvm.base.NetworkResult
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import app.beelabs.com.coconut_mvvm.sample.model.repository.SourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainLiveViewModel @Inject constructor(
    private val repository: SourceRepository
) : BaseViewModel() {

    private val _sources: MutableLiveData<SourceResponse> = MutableLiveData()
    val sources: LiveData<SourceResponse> = _sources

    fun getSourceLiveData() =
        viewModelScope.launch {
            repository.getSourceCaroutine().let { response ->
                _sources.postValue(response.body())
            }
        }

}