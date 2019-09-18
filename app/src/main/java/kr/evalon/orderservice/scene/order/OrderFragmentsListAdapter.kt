package kr.evalon.orderservice.scene.order

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kr.evalon.orderservice.models.ItemCategory

class OrderFragmentsListAdapter(fm:FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val codes = ArrayList<ItemCategory>()

    override fun getCount(): Int {
        return codes.size
    }

    override fun getItem(position: Int): Fragment {
        val category = codes[position]
        return OrderFragment.newInstance(category)
    }

    fun refresh(categories:List<ItemCategory>){
        if(categories.intersect(codes).size == categories.size) return
        codes.clear()
        codes.addAll(categories)
        notifyDataSetChanged()
    }
}