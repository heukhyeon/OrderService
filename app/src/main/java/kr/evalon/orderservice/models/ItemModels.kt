package kr.evalon.orderservice.models

abstract class BaseItem(
    val code:String,
    val name:String,
    val price:Int,
    val categoryCodes: List<String>
)

class MenuItem(
    code:String = "",
    name:String = "",
    price:Int = 0,
    categoryCodes:List<String> = emptyList(),
    val discountPrice:Int = 0,
    val thumbnailUrl:String = ""
): BaseItem(code, name, price, categoryCodes)

class OrderItem(
    code:String,
    name:String,
    price:Int,
    categoryCodes:List<String>,
    count:Int
): BaseItem(code, name, price,categoryCodes){

    var count = count
    private set(value) {
        field = value
    }

    fun plus(item: OrderItem){
        require(code == item.code)
        count += item.count
    }
}