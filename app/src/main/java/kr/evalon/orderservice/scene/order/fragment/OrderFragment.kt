package kr.evalon.orderservice.scene.order.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.evalon.orderservice.livedata.ItemListLiveData
import kr.evalon.orderservice.models.ItemCategory
import kr.evalon.orderservice.models.OrderItem
import kr.evalon.orderservice.scene.order.OrderVm
import kr.evalon.orderservice.scene.order.option.OptionSelectPopup
import org.jetbrains.annotations.TestOnly

class OrderFragment : Fragment() {

    private val adapter = MenuItemOrderAdapter(this)
    private val categoryCode by lazy {
        arguments!!.getString(CATEGORY_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return RecyclerView(container!!.context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        require(view is RecyclerView)
        view.adapter = adapter
        view.layoutManager = GridLayoutManager(requireActivity(), 5)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ItemListLiveData().observe(this, Observer {
            val vmList = it
                .filter { item -> item.categoryCodes.contains(categoryCode) }
                .map { item ->
                    val vm = MenuItemOrderVm(item)
                    vm.clickLiveData.observe(this, Observer { onClickMenuItem(vm) })
                    vm
                }
            adapter.replaceAll(vmList)
        })
    }

    private fun onClickMenuItem(itemVm: MenuItemOrderVm) {
        if (itemVm.optionEnable) {
            OptionSelectPopup.newInstance(itemVm.model.code).show(childFragmentManager,
                OptionSelectPopup::class.java.canonicalName)
        } else
            ViewModelProviders.of(requireActivity()).get(OrderVm::class.java).addItem(itemVm.createOrderItem())

    }

    @TestOnly
    fun getAdapter() = adapter

    companion object {
        const val CATEGORY_KEY = "CATEGORY"

        fun newInstance(category: ItemCategory): OrderFragment {
            val f = OrderFragment()
            val bundle = Bundle()
            bundle.putString(CATEGORY_KEY, category.code)
            f.arguments = bundle
            return f
        }
    }
}