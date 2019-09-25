package kr.evalon.orderservice.scene.temp

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_temp.*
import kr.evalon.orderservice.R

class TempActivity : AppCompatActivity() {

    var expanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)
        btn_temp.setOnClickListener { toggle(!expanded) }
    }

    private fun toggle(enable:Boolean){
        expanded = enable
        val visibility = if(enable) View.VISIBLE else View.GONE
        listOf(temp2,temp3,temp4).forEach { it.visibility = visibility }
        TransitionManager.beginDelayedTransition(layout_temp)
    }
}