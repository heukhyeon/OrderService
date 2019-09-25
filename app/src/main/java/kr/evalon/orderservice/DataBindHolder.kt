package kr.evalon.orderservice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

open class DataBindHolder(parent: ViewGroup, @LayoutRes id: Int) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(id, parent, false)
),
    LifecycleOwner {
    private val holderLifeCycle = LifecycleRegistry(this).apply {
        currentState = Lifecycle.State.INITIALIZED
    }
    val bind = DataBindingUtil.bind<ViewDataBinding>(itemView)!!

    init {
        bind.lifecycleOwner = this
    }

    fun attachWindow(){
        holderLifeCycle.currentState = Lifecycle.State.STARTED
    }

    fun detachWindow(){
        holderLifeCycle.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle {
        return holderLifeCycle
    }
}