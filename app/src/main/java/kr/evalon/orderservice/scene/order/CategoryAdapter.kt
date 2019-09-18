package kr.evalon.orderservice.scene.order

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R
import kr.evalon.orderservice.models.ItemCategory

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

    fun indexOf(category: ItemCategory) = buffer.indexOfFirst { it.code == category.code }
    fun selectChange(i: Int) = buffer.forEachIndexed { index, categoryVm ->
        categoryVm.selected.value = index == i
    }
    fun replaceAll(items:List<CategoryVm>){
        buffer.clear()
        buffer.addAll(items)
        notifyDataSetChanged()
    }
}