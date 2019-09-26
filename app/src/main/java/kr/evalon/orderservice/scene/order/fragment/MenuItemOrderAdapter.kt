package kr.evalon.orderservice.scene.order.fragment

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kr.evalon.orderservice.BR
import kr.evalon.orderservice.DataBindAdapter
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R
import org.jetbrains.annotations.TestOnly

class MenuItemOrderAdapter : DataBindAdapter(){

    private val buffer = ArrayList<MenuItemOrderVm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindHolder {
        return DataBindHolder(parent, R.layout.item_menuitem_order)
    }

    override fun getItemCount(): Int {
        return buffer.size
    }

    override fun onBindViewHolder(holder: DataBindHolder, position: Int) {
        val vm = buffer[position]
        super.onBindViewHolder(holder,position)
        holder.bind.setVariable(BR.vm, vm)
    }

    fun replaceAll(items:List<MenuItemOrderVm>){
        buffer.clear()
        buffer.addAll(items)
        notifyDataSetChanged()
    }

    @TestOnly
    fun getItems() : List<MenuItemOrderVm> = buffer

}