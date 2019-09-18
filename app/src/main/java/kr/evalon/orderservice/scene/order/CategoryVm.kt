package kr.evalon.orderservice.scene.order

import android.graphics.Color
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.evalon.orderservice.models.ItemCategory
import kr.evalon.orderservice.models.OrderItem

class CategoryVm(model: ItemCategory, orderItemsLiveData: LiveData<List<OrderItem>>) {
    val code = model.code
    val name = model.name
    private val countLiveData = Transformations.map(orderItemsLiveData) { itemList ->
        itemList ?: return@map 0
        itemList.filter { item -> item.categoryCodes.contains(model.code) }.map { it.count }.sum()
    }
    val selected = MutableLiveData<Boolean>().apply { value = false }
    val countText = Transformations.map(countLiveData)  { it.toString()}
    val countVisible = Transformations.map(countLiveData){
        it ?: return@map View.INVISIBLE
        if(it > 0) View.VISIBLE
        else View.INVISIBLE
    }
    val selectedBgColor = Transformations.map(selected){
        if(it) Color.CYAN
        else Color.WHITE
    }
    val selectedTextColor = Transformations.map(selected){
        if(it) Color.WHITE
        else Color.BLACK
    }
}