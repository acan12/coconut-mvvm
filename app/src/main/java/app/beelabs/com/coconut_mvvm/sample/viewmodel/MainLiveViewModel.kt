package app.beelabs.com.coconut_mvvm.sample.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.beelabs.coconut.mvvm.base.BaseViewModel
import app.beelabs.coconut.mvvm.base.Resource
import app.beelabs.com.coconut_mvvm.sample.model.api.response.LocationResponse
import app.beelabs.com.coconut_mvvm.sample.model.pojo.LocationEntity
import app.beelabs.com.coconut_mvvm.sample.model.repository.LocationRepository
import app.beelabs.com.coconut_mvvm.sample.domain.usecases.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainLiveViewModel @Inject constructor(
    private val repository: LocationRepository,
    private val locationUseCases: LocationUseCases
) : BaseViewModel() {

    private val _location: MutableLiveData<Resource<LocationResponse>> = MutableLiveData()
    val location: LiveData<Resource<LocationResponse>> = _location

    private val _localLocation: MutableLiveData<List<LocationEntity>> = MutableLiveData()
    val localLocation: LiveData<List<LocationEntity>> = _localLocation

    fun getLocationLiveData(loadingProgress: () -> Unit) =
        viewModelScope.launch {
            loadingProgress.invoke()
            locationUseCases.resultFlow.collect {
                _location.value = it
            }
//            _location.value = repository.getLocationCaroutine()
        }

    fun getLocalLocation(){
        viewModelScope.launch {
            val list = repository.getLocalLocation()
            list.collect { values ->
                _localLocation.postValue(values)
            }
        }
    }

    suspend fun insertLocalLocation(local: LocationEntity, context: Context) {
        repository.insertLocalLocation(local)
    }

}