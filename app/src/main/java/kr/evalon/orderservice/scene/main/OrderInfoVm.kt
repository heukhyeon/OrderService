package kr.evalon.orderservice.scene.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.BR
import kr.evalon.orderservice.DataBindAdapter
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R
import kr.evalon.orderservice.models.OrderInfo

class OrderInfoVm(val model:OrderInfo) {
    val time = model.time
    val status = model.status.message
    val price = model.items.sumBy { it.price }.let {
        String.format("%,d 원", it)
    }
    val adapter = object : DataBindAdapter() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindHolder {
            return DataBindHolder(parent, R.layout.item_last_order_row)
        }

        override fun getItemCount(): Int {
            return model.items.size
        }

        override fun onBindViewHolder(holder: DataBindHolder, position: Int) {
            val item = model.items[position]
            holder.bind.setVariable(BR.name, item.name)
            holder.bind.setVariable(BR.count, item.count)
            holder.bind.setVariable(BR.priceText, String.format("%,d 원", item.price))
        }
    }
}