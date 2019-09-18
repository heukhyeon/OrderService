package kr.evalon.orderservice.scene.order

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.BR
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
        holder.bind.lifecycleOwner = holder.itemView.context as LifecycleOwner
        holder.bind.setVariable(BR.vm, buffer[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,
            DividerItemDecoration.HORIZONTAL))
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