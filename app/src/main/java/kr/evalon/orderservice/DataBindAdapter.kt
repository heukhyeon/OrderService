package kr.evalon.orderservice

import androidx.recyclerview.widget.RecyclerView

abstract class DataBindAdapter : RecyclerView.Adapter<DataBindHolder>() {

    override fun onViewDetachedFromWindow(holder: DataBindHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.detachWindow()
        println("Detached !")
    }

    override fun onViewAttachedToWindow(holder: DataBindHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attachWindow()
        println("Attached !")
    }

    //notifyItemChanged 호출시 itemAnimator 가 존재하면 라이프사이클이 꼬인다.
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
    }
}