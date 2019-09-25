package kr.evalon.orderservice.scene.order.option

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import kr.evalon.orderservice.BR
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.R

class OptionRowViewHolder(parent: ViewGroup) :
    DataBindHolder(parent, R.layout.item_menuitem_option_row) {


    fun bind(vm: OptionSelectRowVm, expanded: Boolean) {
        bind.setVariable(BR.vm, vm)
        val param = itemView.layoutParams ?: ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        param.height = if (expanded) ViewGroup.LayoutParams.WRAP_CONTENT else 0
        itemView.layoutParams = param
    }
}