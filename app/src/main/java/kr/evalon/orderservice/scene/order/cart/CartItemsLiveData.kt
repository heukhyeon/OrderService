package kr.evalon.orderservice.scene.order.cart

import androidx.collection.ArraySet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class CartItemsLiveData : MediatorLiveData<List<CartOrderItemVm>>() {

    private val buffer = ArraySet<String>()

    init {
        value = emptyList()
    }

    override fun setValue(value: List<CartOrderItemVm>?) {
        super.setValue(value)
        if (value == null) {
            setValue(emptyList())
            return
        }
        value.filterNot { buffer.contains(getItemUniqueKey(it)) }
            .forEach { item ->
                val uniqueKey = getItemUniqueKey(item)
                addSource(item.countLiveData) {
                    if (!buffer.contains(uniqueKey)) {
                        buffer.add(uniqueKey)
                    } else {
                        if (it <= 0) {
                            removeSource(item.countLiveData)
                            buffer.remove(uniqueKey)
                        }
                        setValue(this.value)
                    }
                }
            }
    }

    private fun getItemUniqueKey(item: CartOrderItemVm): String {
        return "main:${item.model.code}/${item.model.optionItems.joinToString("/") {
            it.code
        }}"
    }


}