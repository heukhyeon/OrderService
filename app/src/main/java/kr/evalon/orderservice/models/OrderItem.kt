package kr.evalon.orderservice.models

open class OrderItem(
    code:String = "",
    name:String = "",
    price:Int = 0,
    categoryCodes:List<String> = emptyList(),
    count:Int = 0,
    val optionItems: List<OrderItem> = emptyList()
): BaseItem(code, name, price,categoryCodes){

    constructor(item:BaseItem, options:List<OrderItem> = emptyList()):this(
        item.code,
        item.name,
        item.price,
        item.categoryCodes,
        1,
        options)

    var count = count
    private set(value) {
        field = value
    }

    val finalPrice : Int
    get() = (optionItems.map { it.finalPrice }.sum() + price) * count

    override fun equals(other: Any?): Boolean {
        return other is OrderItem &&
                other.code == code &&
                other.name == name &&
                other.optionItems == optionItems
    }

    fun plus(item: OrderItem){
        require(code == item.code)
        plusCart(item.count)
    }

    fun plusCart(delta:Int){
        count += delta
    }
}