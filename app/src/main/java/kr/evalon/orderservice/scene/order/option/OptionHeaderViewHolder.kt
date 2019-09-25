package kr.evalon.orderservice.scene.order.option

import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.item_menuitem_option_header.view.*
import kr.evalon.orderservice.BR
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R

class OptionHeaderViewHolder(parent:ViewGroup) : DataBindHolder(parent,R.layout.item_menuitem_option_header) {


    fun bind(owner: LifecycleOwner, vm:OptionSelectHeaderVm){
        bind.lifecycleOwner = owner
        bind.setVariable(BR.vm, vm)
        val expanded = vm.expandedLiveData.value
        itemView.view_header_direction.rotation = if(expanded == true) 180f else 0f
    }

}