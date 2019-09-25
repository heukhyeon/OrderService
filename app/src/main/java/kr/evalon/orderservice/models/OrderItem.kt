package kr.evalon.orderservice.models

open class OrderItem(
    code:String = "",
    name:String = "",
    price:Int = 0,
    categoryCodes:List<String> = emptyList(),
    count:Int = 0,
    val optionItems: List<OrderItem> = emptyList()
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