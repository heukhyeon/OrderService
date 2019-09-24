package kr.evalon.orderservice.scene.order.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.models.OrderItem

class CartOrderItemVm(val model:OrderItem, val imgUrl:String) {
    private val code = model.code
    val name = model.name
    val countLiveData = MutableLiveData<Int>().apply { value = model.count }
    val countText: LiveData<String> = Transformations.map(countLiveData) {
        it?.toString() ?: model.count.toString()
    }
    val priceText : String = String.format("%,d 원", model.price)
    val totalPrice:Int
    get() = model.price * model.count

    fun has(vm:CartOrderItemVm) = model.code == vm.model.code

    override fun equals(other: Any?): Boolean {
        if(other !is CartOrderItemVm) return false
        return other.code == code && other.countLiveData.value == this.countLiveData.value
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun changeCount(delta:Int){
        if(model.count + delta < 0) return
        model.plusCart(delta)
        countLiveData.value = model.count
    }
}