package app.beelabs.com.coconut_mvvm.sample.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.coconut.mvvm.base.Resource
import app.beelabs.coconut.mvvm.component.dialog.ProgressDialogComponent.Companion.dismissProgressDialog
import app.beelabs.coconut.mvvm.component.dialog.ProgressDialogComponent.Companion.showProgressDialog
import app.beelabs.coconut.mvvm.support.util.NetworkMonitorUtil
import app.beelabs.com.coconut_mvvm.sample.databinding.ActivityMainBinding
import app.beelabs.com.coconut_mvvm.sample.model.api.response.LocationResponse
import app.beelabs.com.coconut_mvvm.sample.ui.interfaces.IMainView
import app.beelabs.com.coconut_mvvm.sample.viewmodel.MainLiveViewModel
import app.beelabs.com.coconut_mvvm.sample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import leakcanary.AppWatcher

@AndroidEntryPoint
class MainActivity : BaseActivity(), IMainView {

    //    @Inject
    private val networkState = NetworkMonitorUtil(this)

    private lateinit var binding: ActivityMainBinding

    private val viewModelRx: MainViewModel by viewModels()
    private val viewModelLive: MainLiveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNetworkMonitoring(networkState)
        // RX Observer
        viewModelRx.getSource(this)

        // coroutine livedata retrofit
//        doCoroutine()
        doLocalData()

        binding.btnRx.setOnClickListener {
            viewModelRx.getSource(this)
        }
        binding.btnCoroutine.setOnClickListener {
            doCoroutine()
        }

        AppWatcher.objectWatcher.watch(this, "View was detached")
    }

    override fun onResume() {
        super.onResume()
        networkState.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelRx.disposable?.dispose()
    }

    fun doLocalData() {
        viewModelLive.getLocalLocation(application)
        viewModelLive.localLocation.observe(this, { result ->
            Log.d("", "")
        })
    }

    fun doCoroutine() {
        showProgressDialog(this, "Coroutine Loading...", false)  // show loading
        viewModelLive.getLocationLiveData()
        viewModelLive.location.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    dismissProgressDialog(this)  // dismiss loading

                    val source = resource.value
                    binding.apply {
                        demoTitle.text = "LiveData: \n${source.locationData[4].name}".also {
                            binding.demoTitle.text = it
                        }
                    }
                }
                is Resource.Error -> {
                    dismissProgressDialog(this)  // dismiss loading
                    if(resource.isNetworkError){
                        Toast.makeText(
                            this@MainActivity,
                            "You lost your connection!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        binding.demoTitle.text = resource.errorBody.toString()
                        Toast.makeText(
                            this@MainActivity,
                            "Error ${resource.errorBody.toString()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun handleSourceResponseData(data: LocationResponse) {
        "Rx: \n${data.locationData[3].name}".also { binding.demoTitle.text = it }
        Toast.makeText(this, "OnNext -> ${data.locationData[3].name}", Toast.LENGTH_SHORT).show()
    }


}