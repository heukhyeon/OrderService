package kr.evalon.orderservice.scene.order.option

import androidx.lifecycle.MutableLiveData
import kr.evalon.orderservice.models.ItemOption
import kr.evalon.orderservice.models.MenuItem

class OptionSelectHeaderVm(val model:ItemOption, val targetItems:List<MenuItem>) {
    val expandedLiveData = MutableLiveData<Boolean>().apply { value = false }

    init {
        require(model.targetItems == targetItems.map { it.code }){
            "ItemOption is Not Matched! " + model.code
        }
    }
    
}