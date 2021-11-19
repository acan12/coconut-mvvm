package app.beelabs.com.coconut_mvvm.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.beelabs.coconut.mvvm.base.BaseViewModel
import app.beelabs.coconut.mvvm.base.Resource
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import app.beelabs.com.coconut_mvvm.sample.model.repository.SourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainLiveViewModel @Inject constructor(
    private val repository: SourceRepository
) : BaseViewModel() {

    private val _sources: MutableLiveData<Resource<SourceResponse>> = MutableLiveData()
    val sources: LiveData<Resource<SourceResponse>> = _sources

    fun getSourceLiveData() =
        viewModelScope.launch {
            _sources.value = repository.getSourceCaroutine()
        }

}