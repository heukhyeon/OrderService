package kr.evalon.orderservice.models

class MenuItem(
    code:String = "",
    name:String = "",
    price:Int = 0,
    categoryCodes:List<String> = emptyList(),
    val discountPrice:Int = 0,
    val thumbnailUrl:String = ""
): BaseItem(code, name, price, categoryCodes)