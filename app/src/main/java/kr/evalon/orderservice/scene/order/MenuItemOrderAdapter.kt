package kr.evalon.orderservice.scene.order

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.BR
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R

class MenuItemOrderAdapter : RecyclerView.Adapter<DataBindHolder>() {
    private val buffer = ArrayList<MenuItemOrderVm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindHolder {
        return DataBindHolder(parent, R.layout.item_menuitem_order)
    }

    override fun getItemCount(): Int {
        return buffer.size
    }

    override fun onBindViewHolder(holder: DataBindHolder, position: Int) {
        val vm = buffer[position]
        holder.bind.setVariable(BR.vm, vm)
    }

    fun replaceAll(items:List<MenuItemOrderVm>){
        buffer.clear()
        buffer.addAll(items)
        notifyDataSetChanged()
    }
}