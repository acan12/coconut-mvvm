package app.beelabs.com.coconut_mvvm.sample.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.coconut.mvvm.base.Resource
import app.beelabs.com.coconut_mvvm.sample.databinding.ActivityMainBinding
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
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

        viewModelLive.getSourceLiveData()
        viewModelLive.sources.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    val source = resource.value
                    binding.apply {
                        demoTitle.text = source.locationData[4].name
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

    override fun handleSourceResponseData(data: SourceResponse) {
        binding.demoTitle.text = data.locationData[4].name
        Toast.makeText(this, "OnNext -> ${data.locationData[3].name}", Toast.LENGTH_SHORT).show()
    }
}