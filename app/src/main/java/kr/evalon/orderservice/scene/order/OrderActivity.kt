package kr.evalon.orderservice.scene.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.evalon.orderservice.livedata.CategoryListLiveData
import kr.evalon.orderservice.models.ItemCategory

class OrderActivity : AppCompatActivity() {
    private val categoryLiveData = CategoryListLiveData()
    private val vm : OrderVm
    get() = ViewModelProviders.of(this).get(OrderVm::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryLiveData.observe(this, Observer(this::onChangeCategoryList))
    }

    private fun onChangeCategoryList(categories:List<ItemCategory>) {
        categories.map { model ->
            val vm = CategoryVm(model, vm.orderItemsLiveData)
            vm.selected.observe(this, Observer {
                if(it) onChangeCategorySelected(model)
            })
        }
    }

    private fun onChangeCategorySelected(category: ItemCategory){

    }
}