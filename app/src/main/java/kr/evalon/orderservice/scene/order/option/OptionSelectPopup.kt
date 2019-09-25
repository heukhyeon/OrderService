package kr.evalon.orderservice.scene.order.option

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.fragment.app.DialogFragment
import kr.evalon.orderservice.R
import kotlin.math.roundToInt
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kr.evalon.orderservice.databinding.PopupOptionSelectBinding


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