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

    private lateinit var binding: ActivityMainBinding

    private val viewModelRx: MainViewModel by viewModels()
    private val viewModelLive: MainLiveViewModel by viewModels()

    private var dialogLostConnection: CoconutAlertNoConnectionDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()

        binding.btnRx.setOnClickListener {
            doRxData()
        }
        binding.btnCoroutine.setOnClickListener {
            doCoroutineData()
        }

        AppWatcher.objectWatcher.watch(this, "View was detached")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelRx.disposable?.dispose()
    }

    fun doRxData(){
        // fetch data from - RX Observer
        viewModelRx.getSource(this)
    }

    // fetch data from - local DB
    fun doLocalData() {
        viewModelLive.getLocalLocation()
    }

    // fetch data from - Coroutine livedata
    fun doCoroutineData() {
        viewModelLive.getLocationLiveData(loadingProgress = {
            binding.loading.show()
            binding.demoTitle.text = "Loading..."
        })
    }

    private fun setupObserver(){
        viewModelLive.localLocation.observe(this, { result ->
            TODO("No action")
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
                            handleNoConnectionInternet()
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
        Toast.makeText(this, "Data -> ${data.locationData[3].name}", Toast.LENGTH_SHORT).show()
    }

    override fun handleNoConnectionInternet() {
        Toast.makeText(this, "Lost connection now!!", Toast.LENGTH_LONG).show()
    }
}