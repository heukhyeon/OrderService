package kr.evalon.orderservice.scene.order

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.BR
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R

class CartOrderItemAdapter : RecyclerView.Adapter<DataBindHolder>(){

    private val buffer = ArrayList<CartOrderItemVm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindHolder {
        return DataBindHolder(parent, R.layout.item_orderitem_cart)
    }

    override fun onBindViewHolder(holder: DataBindHolder, position: Int) {
        holder.bind.lifecycleOwner = holder.itemView.context as LifecycleOwner
        holder.bind.setVariable(BR.vm, buffer[position])
    }

    override fun getItemCount(): Int {
        return buffer.size
    }

    fun refresh(items:List<CartOrderItemVm>){
        items.subtract(buffer).forEach {
            buffer.add(it)
            notifyItemInserted(buffer.size -1)
        }
        buffer.subtract(items).forEach {
            val index = buffer.indexOf(it)
            buffer.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}