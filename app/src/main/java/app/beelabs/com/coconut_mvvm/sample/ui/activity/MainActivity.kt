package app.beelabs.com.coconut_mvvm.sample.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.coconut.mvvm.base.Resource
import app.beelabs.com.coconut_mvvm.sample.databinding.ActivityMainBinding
import app.beelabs.com.coconut_mvvm.sample.model.api.response.LocationResponse
import app.beelabs.com.coconut_mvvm.sample.ui.interfaces.IMainView
import app.beelabs.com.coconut_mvvm.sample.viewmodel.MainLiveViewModel
import app.beelabs.com.coconut_mvvm.sample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), IMainView {

    private lateinit var binding: ActivityMainBinding

    private val viewModelRx: MainViewModel by viewModels()
    private val viewModelLive: MainLiveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RX Observer
        viewModelRx.getSource(this)

        // coroutine livedata retrofit
//        doCoroutine()
        doLocalData()
    }

    fun doLocalData() {
        viewModelLive.getLocalLocation(application)
        viewModelLive.localLocation.observe(this, { result ->
            Log.d("", "")
        })
    }

    fun doCoroutine(){
        viewModelLive.getLocationLiveData()
        viewModelLive.location.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    val source = resource.value
                    binding.apply {
                        demoTitle.text = "LiveData: \n${source.locationData[4].name}".also { binding.demoTitle.text = it }
                    }
                }
                is Resource.Error -> {
                    binding.demoTitle.text = resource.errorBody.toString()
                    Toast.makeText(
                        this@MainActivity,
                        "Error ${resource.errorBody.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun handleSourceResponseData(data: LocationResponse) {
        "Rx: \n${data.locationData[3].name}".also { binding.demoTitle.text = it }
        Toast.makeText(this, "OnNext -> ${data.locationData[3].name}", Toast.LENGTH_SHORT).show()
    }
}