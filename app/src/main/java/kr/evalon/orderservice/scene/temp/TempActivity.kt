package kr.evalon.orderservice.scene.temp

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_temp.*
import kr.evalon.orderservice.R
import kotlin.math.exp

class TempActivity : AppCompatActivity() {

    var expanded1 = true
    var expanded2 = true

    val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = TextView(parent.context)
            view.textSize = 40f
            if (viewType == 0) view.setBackgroundColor(Color.RED)
            return object : RecyclerView.ViewHolder(view) {

            }
        }

        override fun getItemCount(): Int {
            return 5 * 2
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val view = holder.itemView as TextView
            val expanded = if(position < 5) expanded1 else expanded2
            if (position % 5 == 0) {
                view.text = "헤더"
                view.rotation = if(expanded) 0f else 180f
                view.setOnClickListener {
                    if(position == 0) expanded1 = !expanded1
                    else expanded2 = !expanded2
                    notifyItemRangeChanged(position, 5)
                    TransitionManager.beginDelayedTransition(view.parent as ViewGroup)
                }
            } else {
                view.text = "아이템 $position"
                view.visibility = if (expanded) View.VISIBLE else View.GONE
                val param = view.layoutParams ?: ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0)
                param.height = if(expanded) ViewGroup.LayoutParams.WRAP_CONTENT else 0
                view.layoutParams = param
            }

        }

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            super.onAttachedToRecyclerView(recyclerView)
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        }

        override fun getItemViewType(position: Int): Int {
            return if (position % 5 == 0) 0
            else 1
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)
        list_temp.adapter = adapter
//        btn_temp.setOnClickListener { toggle(!expanded) }
    }

//    private fun toggle(enable:Boolean){
//        expanded = enable
//        val visibility = if(enable) View.VISIBLE else View.GONE
//        listOf(temp2,temp3,temp4).forEach { it.visibility = visibility }
//        TransitionManager.beginDelayedTransition(layout_temp)
//    }
}