package kr.evalon.orderservice.scene.order.option

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kr.evalon.orderservice.models.ItemOption
import kr.evalon.orderservice.models.MenuItem

class OptionSelectHeaderVm(val model:ItemOption, targetItems:List<MenuItem>) {
    val expandedLiveData = MutableLiveData<Boolean>().apply { value = false }
    val selectChangedLiveData = MediatorLiveData<OptionSelectRowVm>()
    val childVmList = targetItems.map { OptionSelectRowVm(it, selectChangedLiveData) }

    init {
        val codes = targetItems.map { it.code }.sorted()
        require(model.targetItems.sorted() == codes){
            "ItemOption is Not Matched! ${model.code}\n" +
                    "expected : ${model.targetItems.joinToString()}\n" +
                    "actual : ${codes.joinToString()}"
        }
    }

    fun click() {
        expandedLiveData.value = !(expandedLiveData.value ?: false)
    }
}