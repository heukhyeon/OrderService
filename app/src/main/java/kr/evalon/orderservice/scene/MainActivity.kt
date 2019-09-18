package kr.evalon.orderservice.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kr.evalon.orderservice.R
import kr.evalon.orderservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val vm
        get() = ViewModelProviders.of(this).get(MainVm::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .vm = vm
    }
}