package kr.evalon.orderservice.scene.order.option

import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.DataBindAdapter
import kr.evalon.orderservice.DataBindHolder
import java.lang.IllegalStateException

class OptionSelectAdapter : DataBindAdapter(),
    LifecycleObserver {

    private val buffer = ArrayList<OptionSelectHeaderVm>()
    private val headerIndexes = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindHolder {
        return if (viewType == 0) OptionHeaderViewHolder(parent)
        else OptionRowViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return buffer.sumBy { it.childVmList.size + 1 }
    }

    override fun getItemViewType(position: Int): Int {
        val match = headerIndexes.firstOrNull { it >= position }
        return if (match == position) 0
        else 1
    }

    override fun onBindViewHolder(holder: DataBindHolder, position: Int) {
        val matchIndex = headerIndexes.indexOfFirst { it >= position }
        val realIndex = if(matchIndex != -1) matchIndex else headerIndexes.last()
        val header = buffer[realIndex]
        when (holder) {
            is OptionHeaderViewHolder -> holder.bind(header)
            is OptionRowViewHolder -> {
                val item = header.childVmList[position - headerIndexes[realIndex] - 1]
                holder.bind(item, header.expandedLiveData.value!!)
            }
            else -> throw IllegalStateException()
        }
    }

    fun replaceOptions(options: List<OptionSelectHeaderVm>) {
        buffer.clear()
        headerIndexes.clear()
        var cnt = 0
        options.forEach {
            buffer.add(it)
            headerIndexes.add(cnt)
            cnt += it.childVmList.size + 1
        }
        notifyDataSetChanged()
    }

    fun expandChanged(vm: OptionSelectHeaderVm, parentView: RecyclerView) {
        val index = buffer.indexOf(vm)
        val headerIndex = headerIndexes[index]
        val size = vm.childVmList.size + 1
        notifyItemRangeChanged(headerIndex, size)
        TransitionManager.beginDelayedTransition(parentView)
    }
}