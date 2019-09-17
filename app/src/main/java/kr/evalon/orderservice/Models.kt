package kr.evalon.orderservice


data class OrderInfo(
    val code:String,
    val time:String,
    val status:OrderStatus,
    val items:List<MenuItem>
)

enum class OrderStatus(val message:String){
    NONE("주문이 처리되지 않았습니다"),
    ACCEPT("주방에서 주문을 확인했습니다"),
    PRINT("주방에서 주문서가 출력되었습니다"),
    COMPLETE("주문이 서빙되었습니다")
}

data class ItemCategory(
    val code:String,
    val name:String
)

data class MenuItem(
    val code:String,
    val name:String,
    val price:Int,
    val discountPrice:Int,
    val thumbnailUrl:String,
    val categoryCodes:List<String>
)