package kr.evalon.orderservice.scene.order.option

import android.app.Application
import androidx.lifecycle.*
import kr.evalon.orderservice.livedata.ItemListLiveData

class OptionSelectPopupVm(app:Application, code:String) : AndroidViewModel(app) {
    class Factory(val app:Application, val code:String):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == OptionSelectPopupVm::class.java){
                "Only Support OptionSelectPopupVm!"
            }
            return OptionSelectPopupVm(app, code) as T
        }
    }
    val mainItemLiveData = Transformations.map(ItemListLiveData()){ items->
        requireNotNull(items.find { it.code == code }){
            "Code Not Found ! : ${items.joinToString(" ") {it.code}}"
        }
    }
    val thumbnail = Transformations.map(mainItemLiveData) {
        it ?: return@map ""
        it.thumbnailUrl
    }
    val name: LiveData<String> = Transformations.map(mainItemLiveData){
        it ?: return@map "상품 정보를 불러오고 있습니다..."
        it.name
    }
}