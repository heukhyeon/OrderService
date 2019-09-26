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
) {

    val bind = DataBindingUtil.bind<ViewDataBinding>(itemView)!!

}