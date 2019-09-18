package kr.evalon.orderservice.scene

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kr.evalon.orderservice.livedata.ActionLiveData

class MainVm(app:Application) : AndroidViewModel(app) {
    val buttonText = ObservableField<String>("주문 추가")
    val moveOrder = ActionLiveData()

    fun moveOrderPage(){
        moveOrder.click()
    }

}