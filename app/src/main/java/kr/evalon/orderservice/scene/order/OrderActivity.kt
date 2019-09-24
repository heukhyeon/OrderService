package kr.evalon.orderservice.scene.order

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.transition.addListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_order.*
import kr.evalon.orderservice.R
import kr.evalon.orderservice.databinding.ActivityOrderBinding
import kr.evalon.orderservice.livedata.CategoryListLiveData
import kr.evalon.orderservice.livedata.CreateOrderLiveData
import kr.evalon.orderservice.livedata.debounce
import kr.evalon.orderservice.models.ItemCategory
import kr.evalon.orderservice.models.OrderInfo

class OrderActivity : AppCompatActivity() {
    private val categoryLiveData = CategoryListLiveData()
    private val createOrderLiveData = CreateOrderLiveData()
    private val viewPagerAdapter by lazy { OrderFragmentsListAdapter(supportFragmentManager) }
    private val vm: OrderVm
        get() = ViewModelProviders.of(this).get(OrderVm::class.java)
    private var aniPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
    }

    private fun bind() {
        val bind =
            DataBindingUtil.setContentView<ActivityOrderBinding>(this, R.layout.activity_order)
        bind.lifecycleOwner = this
        bind.vm = vm
        bind.viewPager.adapter = viewPagerAdapter
        bind.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                onViewPagerPageChanged(position)
            }
        })
        categoryLiveData.observe(this, Observer(this::onChangeCategoryList))
        vm.moveToCart.debounce(100L).observe(this, Observer { cartToggle(true) })
        vm.startOrder.debounce(100L).observe(this, Observer { startOrder() })
        createOrderLiveData.observe(this, Observer {
            it ?: return@Observer
            if(it){
                Toast.makeText(this, "주문을 완료했습니다",Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
            else Toast.makeText(this,"주문을 추가하는데 실패했습니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT)
                .show()

        })
    }

    override fun onBackPressed() {
        if (aniPlaying) return
        else if (layout_order_root.isFocusable) cartToggle(false)
        else super.onBackPressed()
    }

    private fun onChangeCategoryList(categories: List<ItemCategory>) {
        val vmList = categories.map { model ->
            val vm = CategoryVm(model, vm.orderItemsLiveData)
            vm.selected.observe(this, Observer {
                if (it) onChangeCategorySelected(model)
            })
            vm
        }
        vm.categoryAdapter.replaceAll(vmList)
        viewPagerAdapter.refresh(categories)
    }

    private fun onChangeCategorySelected(category: ItemCategory) {
        val index = vm.categoryAdapter.indexOf(category)
        if (viewPager.currentItem == index) return
        viewPager.setCurrentItem(index, true)
    }

    private fun onViewPagerPageChanged(position: Int) {
        vm.categoryAdapter.selectChange(position)
    }

    private fun cartToggle(open: Boolean) {
        val set = ConstraintSet()
        set.clone(this, if (open) R.layout.activity_order_cartopen else R.layout.activity_order)
        set.applyTo(layout_order_root)
        val tr = AutoTransition()
        tr.duration = 100L
        tr.addListener(onStart = {
            aniPlaying = true
            if (open) {
                layout_order_root.isClickable = true
                layout_order_root.isFocusable = true
            }
        }, onEnd = {
            if (!open) {
                layout_order_root.isClickable = false
                layout_order_root.isFocusable = false
            }
            aniPlaying = false
        })
        TransitionManager.beginDelayedTransition(layout_order_root, tr)
    }

    private fun startOrder(){
        if(!createOrderLiveData.sendOrder(vm.orderItemsLiveData.value?.map { it.model } ?: emptyList())){
            Toast.makeText(this, "현재 주문을 추가중입니다. 잠시만 기다려주세요...",Toast.LENGTH_SHORT)
                .show()
        }
    }
}