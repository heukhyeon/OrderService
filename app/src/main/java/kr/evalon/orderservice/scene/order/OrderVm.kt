package kr.evalon.orderservice.scene.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class OrderVm(app:Application) : AndroidViewModel(app) {
    val categoryAdapter = CategoryAdapter()
}