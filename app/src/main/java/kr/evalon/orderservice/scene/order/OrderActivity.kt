package kr.evalon.orderservice.scene.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_order.*
import kr.evalon.orderservice.R
import kr.evalon.orderservice.databinding.ActivityOrderBinding
import kr.evalon.orderservice.livedata.CategoryListLiveData
import kr.evalon.orderservice.models.ItemCategory

class OrderActivity : AppCompatActivity() {
    private val categoryLiveData = CategoryListLiveData()
    private val viewPagerAdapter by lazy { OrderFragmentsListAdapter(supportFragmentManager) }
    private val vm : OrderVm
    get() = ViewModelProviders.of(this).get(OrderVm::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind =
            DataBindingUtil.setContentView<ActivityOrderBinding>(this, R.layout.activity_order)
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
    }

    private fun onChangeCategoryList(categories:List<ItemCategory>) {
        val vmList = categories.map { model ->
            val vm = CategoryVm(model, vm.orderItemsLiveData)
            vm.selected.observe(this, Observer {
                if(it) onChangeCategorySelected(model)
            })
            vm
        }
        vm.categoryAdapter.replaceAll(vmList)
        viewPagerAdapter.refresh(categories)
    }

    private fun onChangeCategorySelected(category: ItemCategory){
        val index = vm.categoryAdapter.indexOf(category)
        if(viewPager.currentItem == index) return
        viewPager.setCurrentItem(index, true)
    }

    private fun onViewPagerPageChanged(position:Int){
        vm.categoryAdapter.selectChange(position)
    }
}