package kr.evalon.orderservice.scene.order.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.models.OrderItem
import kr.evalon.orderservice.toFormattedPrice

class CartOrderItemVm(val model:OrderItem, val imgUrl:String) {
    val name = model.name
    val countLiveData = MutableLiveData<Int>().apply { value = model.count }
    val countText: LiveData<String> = Transformations.map(countLiveData) {
        it?.toString() ?: model.count.toString()
    }
    val priceText : String = model.finalPrice.toFormattedPrice()
    val optionText = model.optionItems.take(MAX_OPTION_VISIBLE).mapIndexed { index, orderItem ->
        val isLastTake = index == MAX_OPTION_VISIBLE - 1
        val remainOtherOption = index != model.optionItems.lastIndex
        if(isLastTake && remainOtherOption) "..."
        else "+ ${orderItem.name}"
    }.joinToString("\n")

    fun has(vm:CartOrderItemVm) = model.code == vm.model.code

    override fun equals(other: Any?): Boolean {
        if(other !is CartOrderItemVm) return false
        return other.model == model
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun changeCount(delta:Int){
        if(model.count + delta < 0) return
        model.plusCart(delta)
        countLiveData.value = model.count
    }

    companion object {
        private const val MAX_OPTION_VISIBLE = 3
    }
}