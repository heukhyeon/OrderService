package kr.evalon.orderservice.scene.order

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R

class CategoryAdapter : RecyclerView.Adapter<DataBindHolder>() {
    private val buffer = ArrayList<CategoryVm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindHolder {
        return DataBindHolder(parent, R.layout.item_category)
    }

    override fun getItemCount(): Int {
        return buffer.size
    }

    override fun onBindViewHolder(holder: DataBindHolder, position: Int) {

    }
}