package kr.evalon.orderservice.models

import com.google.firebase.database.FirebaseDatabase
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf


data class OrderInfo(
    val code:String = "",
    val time:String = "",
    val status: OrderStatus = OrderStatus.NONE,
    val items:List<OrderItem> = emptyList()
)

enum class OrderStatus(val message:String){
    NONE("처리 대기중"),
    ACCEPT("주문 확인"),
    PRINT("주문서 출력"),
    COMPLETE("서빙 완료")
}

data class ItemCategory(
    val code:String = "",
    val name:String = ""
)



const val USER_KEY = "USER_KEY"

val userDatabase
get() = FirebaseDatabase.getInstance()
    .reference
    .child(GlobalContext.get().koin.get { parametersOf(USER_KEY) })