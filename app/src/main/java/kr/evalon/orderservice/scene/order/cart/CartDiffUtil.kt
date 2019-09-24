package kr.evalon.orderservice.scene.order.cart

import androidx.recyclerview.widget.DiffUtil

class CartDiffUtil(val oldItems:List<CartOrderItemVm>, val newItems:List<CartOrderItemVm>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return old.model.code == new.model.code
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return old.countLiveData.value == new.countLiveData.value
    }
}