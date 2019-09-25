package kr.evalon.orderservice.scene.order.fragment

import android.view.View
import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.models.MenuItem
import kr.evalon.orderservice.models.OrderItem
import kr.evalon.orderservice.scene.order.cart.CartOrderItemVm

class MenuItemOrderVm(val model:MenuItem) {
    val name = model.name
    val priceText = String.format("%,d 원", model.price)
    val imgUrl = model.thumbnailUrl
    val clickLiveData = ActionLiveData()
    val optionEnable = model.options.isNotEmpty()
    val optionVisible = if(optionEnable) View.VISIBLE else View.INVISIBLE

    fun createOrderItem() = CartOrderItemVm(OrderItem(
        code = model.code,
        name = model.name,
        price = model.price,
        categoryCodes = model.categoryCodes,
        count = 1
    ), imgUrl)

    fun clickItem() = clickLiveData.click()
}