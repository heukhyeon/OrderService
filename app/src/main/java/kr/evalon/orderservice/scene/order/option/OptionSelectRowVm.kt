package kr.evalon.orderservice.scene.order.option

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.models.MenuItem

class OptionSelectRowVm(val model:MenuItem) {
    val selectedLiveData = MutableLiveData<Boolean>().apply { value = false }
    val selectedColorLiveData = Transformations.map(selectedLiveData){
        it ?: return@map
        if(it) Color.GREEN else Color.BLACK
    }
    val selectActionLiveData = ActionLiveData()

    fun select() = selectActionLiveData.click()
}