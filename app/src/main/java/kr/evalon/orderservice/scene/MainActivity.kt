package kr.evalon.orderservice.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.evalon.orderservice.R
import kr.evalon.orderservice.databinding.ActivityMainBinding
import kr.evalon.orderservice.livedata.OrderInfosLiveData

class MainActivity : AppCompatActivity() {

    private val vm
        get() = ViewModelProviders.of(this).get(MainVm::class.java)
    private val orderInfos = OrderInfosLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .vm = vm
        orderInfos.observe(this, Observer {

        })
    }
}