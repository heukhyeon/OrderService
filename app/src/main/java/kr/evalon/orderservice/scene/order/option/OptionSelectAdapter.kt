package kr.evalon.orderservice.scene.order.option

import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.DataBindAdapter
import kr.evalon.orderservice.DataBindHolder
import kr.evalon.orderservice.models.BaseItem
import kr.evalon.orderservice.models.OrderItem
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
        super.onBindViewHolder(holder,position)
        val matchIndex = headerIndexes.indexOfFirst { it > position } - 1
        val realIndex = when(matchIndex){
            -1 -> 0
            -2 -> headerIndexes.lastIndex
            else -> headerIndexes[matchIndex]
        }
        val header = buffer[realIndex]
        println("Pos : $position / index 1 : $realIndex /index 2 : ${headerIndexes[realIndex]}, headrs : ${headerIndexes.joinToString()}")
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

    fun getSelectedItems():List<BaseItem> = buffer
        .mapNotNull { it.selectChangedLiveData.value?.model }
}