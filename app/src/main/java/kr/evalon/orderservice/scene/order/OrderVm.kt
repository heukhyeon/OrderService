package kr.evalon.orderservice.scene.order

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.scene.order.cart.CartItemsLiveData
import kr.evalon.orderservice.scene.order.cart.CartOrderItemAdapter
import kr.evalon.orderservice.scene.order.cart.CartOrderItemVm
import kr.evalon.orderservice.toFormattedPrice

class OrderVm(app:Application) : AndroidViewModel(app) {
    val orderItemsLiveData = CartItemsLiveData()
    val categoryAdapter = CategoryAdapter()
    val cartAdapter = CartOrderItemAdapter()
    val orderButtonText: LiveData<String> = Transformations.map(orderItemsLiveData){ items->
        if(items.isEmpty()) "상품을 먼저 선택해주세요"
        else "주문하기 (${items.sumBy { it.model.count }} 개 상품)"
    }
    val orderButtonClickable: LiveData<Boolean> = Transformations.map(orderItemsLiveData){ items->
        items.any { it.model.count > 0 }
    }
    val orderButtonBg: LiveData<ColorDrawable> = Transformations.map(orderButtonClickable){ enable ->
        if(enable == true) ColorDrawable(Color.CYAN)
        else ColorDrawable(Color.GRAY)
    }
    val totalPriceText: LiveData<String> = Transformations.map(orderItemsLiveData){ items->
        val price =  items.sumBy { it.model.finalPrice }
        price.toFormattedPrice()
    }
    val orderAcceptText: LiveData<String> = Transformations.map(orderButtonClickable){ enable->
        if(enable != true) "주문할 상품이 없습니다"
        else "주문확정"
    }

    val moveToCart = ActionLiveData()
    val startOrder = ActionLiveData()

    fun addItem(item:CartOrderItemVm){
        val items = orderItemsLiveData.value ?: emptyList()
        val target = items.find { item == it }
        if(target == null) orderItemsLiveData.setValue(items + item)
        else {
            target.changeCount(item.model.count)
            orderItemsLiveData.postValue(items)
        }
    }
    fun openCart()= moveToCart.click()
    fun startOrder() = startOrder.click()
}