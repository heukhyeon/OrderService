package kr.evalon.orderservice.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kr.evalon.orderservice.models.OrderInfo
import kr.evalon.orderservice.models.OrderItem
import kr.evalon.orderservice.models.OrderStatus
import kr.evalon.orderservice.models.userDatabase
import java.text.SimpleDateFormat
import java.util.*

class CreateOrderLiveData : LiveData<Boolean>() {

    private var sending = false

    @Synchronized
    fun sendOrder(items: List<OrderItem>):Boolean{
        if(sending) return false
        sending = true
        userDatabase.child("orders")
            .orderByKey()
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    sending = false
                    value = false
                }

                override fun onDataChange(p0: DataSnapshot) {
                    onAcceptLastOrderKey(p0,items)
                }
            })
        return true

    }

    private fun onAcceptLastOrderKey(p0: DataSnapshot, items:List<OrderItem>) {
        val key = p0.children.firstOrNull()?.getValue(OrderInfo::class.java)?.code ?: "0000"
        val nextKey = (key.toInt() + 1).toString().padStart(4, '0')
        val time = Date()
        val info = OrderInfo(
            code = nextKey,
            time = SimpleDateFormat("MM-dd hh:mm:ss", Locale.US).format(time),
            status = OrderStatus.NONE,
            items = items)
        val ref = userDatabase.child("orders/${nextKey}").setValue(info)
        ref.addOnCompleteListener {
            sending = false
            value = it.isSuccessful
        }
    }
}