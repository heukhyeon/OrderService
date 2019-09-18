package kr.evalon.orderservice.scene.order

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.models.ThumbnailOrderItem

class OrderVm(app:Application) : AndroidViewModel(app) {
    val orderItemsLiveData = MutableLiveData<List<ThumbnailOrderItem>>().apply { value = emptyList() }
    val categoryAdapter = CategoryAdapter()
    val cartAdapter = CartOrderItemAdapter()
    val orderButtonText: LiveData<String> = Transformations.map(orderItemsLiveData){ items->
        if(items.isEmpty()) "상품을 먼저 선택해주세요"
        else "주문하기 (${items.sumBy { it.count }} 개 상품)"
    }
    val orderButtonClickable: LiveData<Boolean> = Transformations.map(orderItemsLiveData){
        it.isNotEmpty()
    }
    val orderButtonBg: LiveData<ColorDrawable> = Transformations.map(orderItemsLiveData){
        if(it.isNotEmpty()) ColorDrawable(Color.CYAN)
        else ColorDrawable(Color.GRAY)
    }
    val totalPriceText: LiveData<String> = Transformations.map(orderItemsLiveData){ items->
        val price =  items.sumBy { it.price * it.count }
        String.format("%,d 원", price)
    }
    val cartItemLiveData = Transformations.map(orderItemsLiveData) {items->
        items.map { CartOrderItemVm(it) }
    }

    val moveToCart = ActionLiveData()

    fun addItem(item:ThumbnailOrderItem){
        val items = orderItemsLiveData.value ?: emptyList()
        val target = items.find { item.code == it.code }
        if(target == null) orderItemsLiveData.setValue(items + item)
        else {
            target.plus(item)
            orderItemsLiveData.postValue(items)
        }
    }
    fun openCart()=moveToCart.click()
}