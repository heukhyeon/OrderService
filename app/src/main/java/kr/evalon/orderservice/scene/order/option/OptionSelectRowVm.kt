package kr.evalon.orderservice.scene.order.option

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.bumptech.glide.RequestBuilder
import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.models.MenuItem

class OptionSelectRowVm(val model:MenuItem, selectGroupLiveData:MediatorLiveData<OptionSelectRowVm>) {
    private val selectedLiveData: LiveData<Boolean> = Transformations.map(selectGroupLiveData){
        it == this
    }
    private val selectActionLiveData = ActionLiveData()

    val selectedColorLiveData: LiveData<Int> = Transformations.map(selectedLiveData) {
        it ?: return@map Color.BLACK
        if(it) return@map Color.GREEN else return@map Color.BLACK
    }

    init {
        selectGroupLiveData.addSource(selectActionLiveData){
            it ?: return@addSource
            selectGroupLiveData.value = this
        }
    }

    fun select() {
        selectActionLiveData.click()
    }

    fun createOptionThumbnail(requestBuilder: RequestBuilder<Drawable>): RequestBuilder<Drawable> {
        return requestBuilder.load(model.thumbnailUrl)
            .circleCrop()
    }
}