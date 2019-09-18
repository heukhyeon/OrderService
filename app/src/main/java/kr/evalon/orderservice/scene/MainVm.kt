package kr.evalon.orderservice.scene

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

class MainVm(app:Application) : AndroidViewModel(app) {
    val buttonText = ObservableField<String>("주문 추가")

}