package app.beelabs.coconut.mvvm.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

}