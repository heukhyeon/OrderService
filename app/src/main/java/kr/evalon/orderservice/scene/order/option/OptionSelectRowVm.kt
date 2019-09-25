package kr.evalon.orderservice.scene.order.option

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.models.MenuItem

class OptionSelectRowVm(val model:MenuItem) {
    val selectedLiveData = MutableLiveData<Boolean>().apply { value = false }
    val selectedColorLiveData: LiveData<Int> = Transformations.map(selectedLiveData) {
        it ?: return@map Color.BLACK
        if(it) return@map Color.GREEN else return@map Color.BLACK
    }

    val selectActionLiveData = ActionLiveData()

    fun select() = selectActionLiveData.click()
}