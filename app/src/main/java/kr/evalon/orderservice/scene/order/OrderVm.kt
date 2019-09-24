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
import kr.evalon.orderservice.scene.order.cart.CartOrderItemAdapter
import kr.evalon.orderservice.scene.order.cart.CartOrderItemVm

class OrderVm(app:Application) : AndroidViewModel(app) {
    val orderItemsLiveData = MutableLiveData<List<ThumbnailOrderItem>>().apply { value = emptyList() }
    val categoryAdapter = CategoryAdapter()
    val cartAdapter = CartOrderItemAdapter()
    val orderButtonText: LiveData<String> = Transformations.map(orderItemsLiveData){ items->
        if(items.isEmpty()) "상품을 먼저 선택해주세요"
        else "주문하기 (${items.sumBy { it.count }} 개 상품)"
    }
    val orderButtonClickable: LiveData<Boolean> = Transformations.map(orderItemsLiveData){ items->
        items.any { it.count > 0 }
    }
    val orderButtonBg: LiveData<ColorDrawable> = Transformations.map(orderButtonClickable){ enable ->
        if(enable == true) ColorDrawable(Color.CYAN)
        else ColorDrawable(Color.GRAY)
    }
    val totalPriceText: LiveData<String> = Transformations.map(orderItemsLiveData){ items->
        val price =  items.sumBy { it.price * it.count }
        String.format("%,d 원", price)
    }
    val orderAcceptText: LiveData<String> = Transformations.map(orderButtonClickable){ enable->
        if(enable != true) "주문할 상품이 없습니다"
        else "주문확정"
    }
    val cartItemLiveData: LiveData<List<CartOrderItemVm>> = Transformations.map(orderItemsLiveData) { items->
        items.map { CartOrderItemVm(it) }
    }

    val moveToCart = ActionLiveData()
    val startOrder = ActionLiveData()

    fun addItem(item:ThumbnailOrderItem){
        val items = orderItemsLiveData.value ?: emptyList()
        val target = items.find { item.code == it.code }
        if(target == null) orderItemsLiveData.setValue(items + item)
        else {
            target.plus(item)
            orderItemsLiveData.postValue(items)
        }
    }
    fun openCart()= moveToCart.click()
    fun startOrder() = startOrder.click()
}