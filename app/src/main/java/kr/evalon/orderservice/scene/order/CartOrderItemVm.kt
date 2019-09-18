package kr.evalon.orderservice.scene.order

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.models.ThumbnailOrderItem

class CartOrderItemVm(val model:ThumbnailOrderItem) {
    private val code = model.code
    val name = model.name
    val imgUrl = model.thumbnailUrl
    val countLiveData = MutableLiveData<Int>().apply { value = null }
    val countText: LiveData<String> = Transformations.map(countLiveData) {
        it.toString()
    }
    val priceText : String = String.format("%,d 원", model.price)

    override fun equals(other: Any?): Boolean {
        if(other !is CartOrderItemVm) return false
        return other.code == code
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun changeCount(delta:Int){
        if(model.count - delta < 0) return
        model.plusCart(delta)
        countLiveData.value = model.count
    }
}