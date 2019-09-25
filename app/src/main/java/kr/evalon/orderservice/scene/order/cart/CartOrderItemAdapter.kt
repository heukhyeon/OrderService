package kr.evalon.orderservice.scene.order.cart

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.BR
import kr.evalon.orderservice.DataBindAdapter
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R



class CartOrderItemAdapter : DataBindAdapter() {

    private val buffer = ArrayList<CartOrderItemVm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindHolder {
        return DataBindHolder(parent, R.layout.item_orderitem_cart)
    }

    override fun onBindViewHolder(holder: DataBindHolder, position: Int) {
        holder.bind.setVariable(BR.vm, buffer[position])
    }

    override fun getItemCount(): Int {
        return buffer.size
    }

    fun refresh(targets:List<CartOrderItemVm>){
        val items = removeWhenCount0(targets)
        val util = CartDiffUtil(buffer,items)
        val diffResult = DiffUtil.calculateDiff(util)
        buffer.clear()
        buffer.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun removeWhenCount0(items:List<CartOrderItemVm>): List<CartOrderItemVm> {
        return items.filterNot { it.countLiveData.value == 0 }
    }
}