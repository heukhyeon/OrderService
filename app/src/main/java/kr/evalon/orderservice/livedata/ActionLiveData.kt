package kr.evalon.orderservice.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class ActionLiveData : LiveData<Int>() {

    private var cnt = 0

    override fun observe(owner: LifecycleOwner, observer: Observer<in Int>) {
        require(!hasObservers()){
            "${javaClass.name} : only support Single Observer!"
        }
        super.observe(owner, Observer<Int> {
            it ?: return@Observer
            observer.onChanged(it)
            value = null
        })
    }

    fun click() {
        value = cnt++
    }
}