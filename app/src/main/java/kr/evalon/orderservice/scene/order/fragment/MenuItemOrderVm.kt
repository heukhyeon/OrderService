package kr.evalon.orderservice.scene.order.fragment

import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.models.MenuItem
import kr.evalon.orderservice.models.CartItem

class MenuItemOrderVm(val model:MenuItem) {
    val name = model.name
    val priceText = String.format("%,d 원", model.price)
    val imgUrl = model.thumbnailUrl
    val clickLiveData = ActionLiveData()


    fun createOrderItem() = CartItem(model.code,model.name,model.price,model.categoryCodes, 1, imgUrl)
    fun clickItem() = clickLiveData.click()
}