package kr.evalon.orderservice.scene.order.cart

import androidx.collection.ArraySet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class CartItemsLiveData: MediatorLiveData<List<CartOrderItemVm>>() {

    private val buffer = ArraySet<String>()

    init {
        value = emptyList()
    }

    override fun setValue(value: List<CartOrderItemVm>?) {
        super.setValue(value)
        if(value == null){
            setValue(emptyList())
            return
        }
        value.filterNot { buffer.contains(it.model.code) }
            .forEach { item->
                addSource(item.countLiveData){
                    if(!buffer.contains(item.model.code)){
                        buffer.add(item.model.code)
                    }
                    else {
                        if(it <= 0){
                            removeSource(item.countLiveData)
                            buffer.remove(item.model.code)
                        }
                        setValue(this.value)
                    }
                }
            }
    }
}