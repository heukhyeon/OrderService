package kr.evalon.orderservice

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

abstract class DataBindAdapter : RecyclerView.Adapter<DataBindHolder>(), LifecycleOwner {
    private val registry = LifecycleRegistry(this)

    init {
        registry.currentState = Lifecycle.State.INITIALIZED
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
        registry.currentState = Lifecycle.State.STARTED
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        registry.currentState = Lifecycle.State.DESTROYED
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onBindViewHolder(holder: DataBindHolder, position: Int) {
        holder.bind.lifecycleOwner = this
    }

    override fun getLifecycle(): Lifecycle {
        return registry
    }


}