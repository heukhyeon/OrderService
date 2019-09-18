package kr.evalon.orderservice.scene.order

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.models.ThumbnailOrderItem

class OrderVm(app:Application) : AndroidViewModel(app) {
    val orderItemsLiveData = MutableLiveData<List<ThumbnailOrderItem>>().apply { value = emptyList() }
    val categoryAdapter = CategoryAdapter()
    val orderButtonText = Transformations.map(orderItemsLiveData){items->
        if(items.isEmpty()) "상품을 먼저 선택해주세요"
        else "주문하기 (${items.sumBy { it.count }} 개 상품)"
    }
    val orderButtonClickable = Transformations.map(orderItemsLiveData){
        it.isNotEmpty()
    }
    val orderButtonBg = Transformations.map(orderItemsLiveData){
        if(it.isNotEmpty()) ColorDrawable(Color.CYAN)
        else ColorDrawable(Color.GRAY)
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