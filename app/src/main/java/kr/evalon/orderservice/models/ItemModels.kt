package kr.evalon.orderservice.models

abstract class BaseItem(
    val code:String,
    val name:String,
    val price:Int,
    val categoryCodes: List<String>
)

class MenuItem(
    code:String,
    name:String,
    price:Int,
    categoryCodes:List<String>,
    val discountPrice:Int,
    val thumbnailUrl:String
): BaseItem(code, name, price, categoryCodes)

class OrderItem(
    code:String,
    name:String,
    price:Int,
    categoryCodes:List<String>,
    val count:Int
): BaseItem(code, name, price,categoryCodes)