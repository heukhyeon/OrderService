package kr.evalon.orderservice.scene.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kr.evalon.orderservice.models.OrderItem

class OrderVm(app:Application) : AndroidViewModel(app) {
    val orderItemsLiveData = MutableLiveData<List<OrderItem>>().apply { value = emptyList() }
    val categoryAdapter = CategoryAdapter()

}