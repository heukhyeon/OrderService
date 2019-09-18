package kr.evalon.orderservice.livedata

import android.os.Build
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import kr.evalon.orderservice.OrderInfo

class OrderInfosLiveData : LiveData<List<OrderInfo>>(), ValueEventListener {

    private val ref by lazy {
        FirebaseDatabase.getInstance()
            .reference
            .child(Build.SERIAL)
            .child("orders")
    }

    override fun onActive() {
        super.onActive()
        ref.addValueEventListener(this)
    }

    override fun onInactive() {
        ref.removeEventListener(this)
        super.onInactive()
    }

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(p0: DataSnapshot) {
        val value = p0.getValue(object : GenericTypeIndicator<ArrayList<OrderInfo>>() {

        })
        postValue(value)
    }
}