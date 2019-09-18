package kr.evalon.orderservice.scene.main

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.livedata.OrderInfosLiveData

class MainVm(app:Application) : AndroidViewModel(app) {
    private val orderInfos = OrderInfosLiveData()
    val storeName = "테스트 가맹점"
    val lastOrderInfoLiveData: LiveData<OrderInfoVm?> = Transformations.map(orderInfos) { orders->
        if(orders.isEmpty()) return@map null
        OrderInfoVm(orders.maxBy { it.code }!!)
    }
    val buttonText: LiveData<String> = Transformations.map(orderInfos) { orders->
        if(orders.isEmpty()) "주문 시작"
        else "주문 추가"
    }

    val moveOrder = ActionLiveData()

    fun moveOrderPage(){
        moveOrder.click()
    }

}