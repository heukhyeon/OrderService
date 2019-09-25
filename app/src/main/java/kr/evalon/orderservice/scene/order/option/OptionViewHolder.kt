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

class OptionViewHolder(parent:ViewGroup) : DataBindHolder(parent,R.layout.item_menuitem_option_header) {


    fun bind(owner: LifecycleOwner, vm:OptionSelectHeaderVm){
        bind.lifecycleOwner = owner
        bind.setVariable(BR.vm, vm)
        val viewExpanded = itemView.list_option_rows.visibility == View.VISIBLE
        val modelExpanded = requireNotNull(vm.expandedLiveData.value)
        if(viewExpanded != modelExpanded){
            itemView.list_option_rows.visibility = getExpandVisibility(modelExpanded)
        }
        vm.expandedLiveData.removeObservers(owner)
        vm.expandedLiveData.observe(owner, Observer {
            requireNotNull(it)
            val expandVisibility = getExpandVisibility(it)
            val currentVisibility = itemView.list_option_rows.visibility
            if(expandVisibility == currentVisibility) return@Observer
            itemView.list_option_rows.visibility = expandVisibility
            TransitionManager.beginDelayedTransition(itemView as ViewGroup)
        })
    }

    private fun getExpandVisibility(expand:Boolean) = if(expand) View.VISIBLE else View.GONE
}