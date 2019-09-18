package kr.evalon.orderservice.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kr.evalon.orderservice.ItemCategory
import kr.evalon.orderservice.MenuItem
import kr.evalon.orderservice.userDatabase

class ItemListLiveData : LiveData<List<MenuItem>>(), ValueEventListener {

   private val ref by lazy {
        userDatabase.child("items")
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
        val type = object : GenericTypeIndicator<HashMap<String, MenuItem>>() {

        }
        postValue(
            p0.getValue(type)?.values?.toList() ?: emptyList()
        )
    }
}