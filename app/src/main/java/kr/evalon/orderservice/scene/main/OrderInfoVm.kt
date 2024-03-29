package kr.evalon.orderservice.scene.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.*
import kr.evalon.orderservice.models.OrderInfo

class OrderInfoVm(val model:OrderInfo) {
    val time = model.time
    val status = model.status.message
    val price = model.items.sumBy { it.price }.let {
        it.toFormattedPrice()
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
            super.onBindViewHolder(holder,position)
            holder.bind.setVariable(BR.name, item.name)
            holder.bind.setVariable(BR.count, item.count)
            holder.bind.setVariable(BR.priceText, item.price.toFormattedPrice())
        }
    }
}