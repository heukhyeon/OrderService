package kr.evalon.orderservice.models

abstract class BaseItem(
    val code:String,
    val name:String,
    val price:Int
)

class MenuItem(
    code:String,
    name:String,
    price:Int,
    val discountPrice:Int,
    val thumbnailUrl:String,
    val categoryCodes:List<String>
): BaseItem(code, name, price)

class OrderItem(
    code:String,
    name:String,
    price:Int,
    val count:Int
): BaseItem(code, name, price)