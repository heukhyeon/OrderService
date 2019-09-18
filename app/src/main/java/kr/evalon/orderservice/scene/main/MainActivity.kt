package kr.evalon.orderservice.scene.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.evalon.orderservice.R
import kr.evalon.orderservice.databinding.ActivityMainBinding
import kr.evalon.orderservice.livedata.OrderInfosLiveData
import kr.evalon.orderservice.livedata.debounce

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
        vm.moveOrder.debounce(500L).observe(this, Observer {

        })
    }
}