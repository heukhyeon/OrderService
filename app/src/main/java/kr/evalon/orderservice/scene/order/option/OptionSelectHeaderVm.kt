package kr.evalon.orderservice.scene.order.option

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kr.evalon.orderservice.models.ItemOption
import kr.evalon.orderservice.models.MenuItem

class OptionSelectHeaderVm(val model:ItemOption, targetItems:List<MenuItem>) {
    val expandedLiveData = MutableLiveData<Boolean>().apply { value = false }
    val childVmList = targetItems.map { OptionSelectRowVm(it, selectChangedLiveData) }
    private val selectChangedLiveData = MediatorLiveData<OptionSelectRowVm>()

    init {
        require(model.targetItems == targetItems.map { it.code }){
            "ItemOption is Not Matched! " + model.code
        }
    }
}