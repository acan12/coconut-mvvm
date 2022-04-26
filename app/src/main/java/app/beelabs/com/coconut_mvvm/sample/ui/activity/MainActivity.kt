package app.beelabs.com.coconut_mvvm.sample.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.coconut.mvvm.base.Resource
import app.beelabs.coconut.mvvm.base.exception.NetworkLostConnectionException
import app.beelabs.coconut.mvvm.support.dialog.CoconutAlertNoConnectionDialog
import app.beelabs.com.coconut_mvvm.sample.databinding.ActivityMainBinding
import app.beelabs.com.coconut_mvvm.sample.model.api.response.LocationResponse
import app.beelabs.com.coconut_mvvm.sample.ui.interfaces.IMainView
import app.beelabs.com.coconut_mvvm.sample.viewmodel.MainLiveViewModel
import app.beelabs.com.coconut_mvvm.sample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import leakcanary.AppWatcher

@AndroidEntryPoint
class MainActivity : BaseActivity(), IMainView {

//    private val networkState = NetworkMonitorUtil(this)

    private lateinit var binding: ActivityMainBinding

    private val viewModelRx: MainViewModel by viewModels()
    private val viewModelLive: MainLiveViewModel by viewModels()

    private var dialogLostConnection: CoconutAlertNoConnectionDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupNetworkMonitoring(networkState)
        // fetch data from - RX Observer
//        viewModelRx.getSource(this)

        // fetch data from - Coroutine livedata
//        doCoroutine()

        // fetch data from - local DB
//        doLocalData()

        binding.btnRx.setOnClickListener {
            viewModelRx.getSource(this)
        }
        binding.btnCoroutine.setOnClickListener {
            doCoroutine()
        }

        AppWatcher.objectWatcher.watch(this, "View was detached")
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
        viewModelLive.getLocationLiveData(loadingProgress = {
            binding.loading.show()
            binding.demoTitle.text = "Loading..."
        })
        viewModelLive.location.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.loading.hide()

                        val source = resource.value
                        binding.apply {
                            demoTitle.text = "LiveData: \n${source.locationData[4].name}".also {
                                binding.demoTitle.text = it
                            }
                        }
                    }, 600)
                }
                is Resource.Error -> {
                    binding.loading.hide()

                    when (resource.throwable) {
                        is NetworkLostConnectionException -> {
                            binding.demoTitle.text = resource.throwable.message
                            dialogLostConnection?.dismiss()
                            dialogLostConnection = CoconutAlertNoConnectionDialog(this)
                            dialogLostConnection?.show()
                        }
                        else -> {
                            binding.demoTitle.text = resource.errorBody.toString()
                            Toast.makeText(
                                this@MainActivity,
                                "Error ${resource.errorBody.toString()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun handleSourceResponseData(data: LocationResponse) {
        "Rx: \n${data.locationData[3].name}".also { binding.demoTitle.text = it }
        Toast.makeText(this, "OnNext -> ${data.locationData[3].name}", Toast.LENGTH_SHORT).show()
    }
}