package kr.evalon.orderservice.scene.order.option

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.models.ItemOption
import kr.evalon.orderservice.models.MenuItem

class OptionSelectHeaderVm(val model:ItemOption, targetItems:List<MenuItem>) {
    val expandedLiveData = MutableLiveData<Boolean>().apply { value = false }
    val selectChangedLiveData = MediatorLiveData<OptionSelectRowVm>().apply { value = null }
    val childVmList = targetItems.map { OptionSelectRowVm(it, selectChangedLiveData) }
    val selectCompleted: LiveData<Boolean> = Transformations.map(selectChangedLiveData){
        it != null
    }
    val selectedItemText: LiveData<String> = Transformations.map(selectChangedLiveData){
        it?.model?.name ?: ""
    }

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