package app.beelabs.com.coconut_mvvm.sample.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import app.beelabs.coconut.mvvm.base.BaseActivity
import app.beelabs.com.coconut_mvvm.sample.R
import app.beelabs.com.coconut_mvvm.sample.databinding.ActivityMainBinding
import app.beelabs.com.coconut_mvvm.sample.model.api.response.SourceResponse
import app.beelabs.com.coconut_mvvm.sample.ui.interfaces.IMainView
import app.beelabs.com.coconut_mvvm.sample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), IMainView {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSource(this)
    }

    override fun handleSourceResponseData(data: SourceResponse) {
        binding.demoTitle.text = data.locationData[4].name
        Toast.makeText(this, "OnNext -> ${data.locationData[3].name}", Toast.LENGTH_SHORT).show()
    }
}