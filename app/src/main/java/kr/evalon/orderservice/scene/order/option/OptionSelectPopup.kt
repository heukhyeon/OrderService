package kr.evalon.orderservice.scene.order.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kr.evalon.orderservice.R
import kotlin.math.roundToInt
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.popup_option_select.*
import kr.evalon.orderservice.databinding.PopupOptionSelectBinding
import kr.evalon.orderservice.livedata.ItemListLiveData
import kr.evalon.orderservice.livedata.debounce
import kr.evalon.orderservice.models.MenuItem
import kr.evalon.orderservice.models.OrderItem
import kr.evalon.orderservice.scene.order.OrderVm
import kr.evalon.orderservice.scene.order.cart.CartOrderItemVm


class OptionSelectPopup : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.popup_option_select, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.OptionSelectPopupStyle)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog ?: return
        val width = (resources.displayMetrics.widthPixels * 0.9).roundToInt()
        val height = (resources.displayMetrics.heightPixels * 0.9).roundToInt()
        dialog.window!!.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bind = DataBindingUtil.bind<PopupOptionSelectBinding>(view)
        requireNotNull(bind)
        val code = requireNotNull(arguments?.getString(TARGET_ITEM))
        val vm = ViewModelProviders.of(this,
            OptionSelectPopupVm.Factory(requireActivity().application, code))
            .get(OptionSelectPopupVm::class.java)
        bind.lifecycleOwner = this
        bind.vm = vm
        ItemListLiveData().observe(this, Observer(this::onReceiveItemList))
        vm.actionCompleteSelect.debounce(200L).observe(this, Observer {
            onSelectComplete()
        })
    }

    private fun onReceiveItemList(items:List<MenuItem>){
        val code = requireNotNull(arguments?.getString(TARGET_ITEM))
        val vm = ViewModelProviders.of(this,
            OptionSelectPopupVm.Factory(requireActivity().application, code))
            .get(OptionSelectPopupVm::class.java)
        val targetItem = items.first { it.code == code }
        val targetList = list_options
        vm.adapter.replaceOptions(targetItem.options.map {option->
            val childes = items.filter { it.code in option.targetItems }
            val header = OptionSelectHeaderVm(option, childes)
            header.expandedLiveData.observe(this, Observer {
                if(vm.adapter.itemCount == 0) return@Observer
                vm.adapter.expandChanged(header, requireNotNull(targetList))
            })
            header.selectChangedLiveData.observe(this, Observer {
                it ?: return@Observer
                vm.optionSelectedLiveData.value = vm.adapter.getSelectedItems()
            })
            header
        })
    }

    private fun onSelectComplete(){
        val vm = ViewModelProviders.of(this).get(OptionSelectPopupVm::class.java)
        val notComplete = vm.adapter.getHeaders().find { it.selectChangedLiveData.value == null }
        if(notComplete != null){
            Toast.makeText(requireActivity(), "${notComplete.model.name} 항목을 선택해주세요",
                Toast.LENGTH_SHORT).show()
            return
        }
        val childes = vm.adapter.getSelectedItems()
        val mainItem = requireNotNull(vm.mainItemLiveData.value)
        val resultOrderItem = OrderItem(mainItem, childes.map { OrderItem(it)})
        ViewModelProviders.of(requireActivity()).get(OrderVm::class.java)
            .addItem(CartOrderItemVm(resultOrderItem, mainItem.thumbnailUrl))
        dismiss()
    }

    companion object {
        private const val TARGET_ITEM = "TARGET_ITEM"

        fun newInstance(code:String): OptionSelectPopup {
            val f = OptionSelectPopup()
            val b = Bundle()
            b.putString(TARGET_ITEM, code)
            f.arguments = b
            return f
        }
    }
}