package kr.evalon.orderservice

import androidx.recyclerview.widget.RecyclerView

abstract class DataBindAdapter : RecyclerView.Adapter<DataBindHolder>() {

    override fun onViewDetachedFromWindow(holder: DataBindHolder) {
        holder.detachWindow()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: DataBindHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attachWindow()
    }
}