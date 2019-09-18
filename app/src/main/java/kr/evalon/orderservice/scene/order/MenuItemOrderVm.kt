package kr.evalon.orderservice.scene.order

import kr.evalon.orderservice.livedata.ActionLiveData
import kr.evalon.orderservice.models.MenuItem

class MenuItemOrderVm(val model:MenuItem) {
    val name = model.name
    val priceText = "${String.format("#,d",model.price)} 원"
    val imgUrl = model.thumbnailUrl
    val clickLiveData = ActionLiveData()


    fun clickItem() = clickLiveData.click()
}