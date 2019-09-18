package kr.evalon.orderservice.models

import com.google.firebase.database.FirebaseDatabase
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf


data class OrderInfo(
    val code:String,
    val time:String,
    val status: OrderStatus,
    val items:List<OrderItem>
)

enum class OrderStatus(val message:String){
    NONE("주문이 처리되지 않았습니다"),
    ACCEPT("주방에서 주문을 확인했습니다"),
    PRINT("주방에서 주문서가 출력되었습니다"),
    COMPLETE("주문이 서빙되었습니다")
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