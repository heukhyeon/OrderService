package kr.evalon.orderservice.scene.order.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.evalon.orderservice.R
import kr.evalon.orderservice.scene.order.fragment.MenuItemOrderVm

class OptionSelectPopup : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.popup_option_select, container, false)
    }

    companion object {
        private const val TARGET_ITEM = "TARGET_ITEM"

        fun newInstance(item:MenuItemOrderVm): OptionSelectPopup {
            val f = OptionSelectPopup()
            val b = Bundle()
            b.putString(TARGET_ITEM, item.model.code)
            f.arguments = b
            return f
        }
    }
}