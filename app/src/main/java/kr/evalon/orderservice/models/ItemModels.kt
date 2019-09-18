package kr.evalon.orderservice.models

import com.google.firebase.database.Exclude

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

open class OrderItem(
    code:String = "",
    name:String = "",
    price:Int = 0,
    categoryCodes:List<String> = emptyList(),
    count:Int = 0
): BaseItem(code, name, price,categoryCodes){

    var count = count
    private set(value) {
        field = value
    }

    fun plus(item: OrderItem){
        require(code == item.code)
        plusCart(item.count)
    }

    fun plusCart(delta:Int){
        count += delta
    }
}

class ThumbnailOrderItem(
    code:String,
    name:String,
    price:Int,
    categoryCodes:List<String>,
    count:Int,
    @Exclude
    val thumbnailUrl: String
) : OrderItem(code, name, price, categoryCodes, count)
