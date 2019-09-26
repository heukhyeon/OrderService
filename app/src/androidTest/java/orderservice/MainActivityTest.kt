package orderservice

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.runner.AndroidJUnit4
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.activity_order.*
import kr.evalon.orderservice.livedata.CategoryListLiveData
import kr.evalon.orderservice.livedata.ItemListLiveData
import kr.evalon.orderservice.models.OrderItem
import kr.evalon.orderservice.scene.order.OrderActivity
import kr.evalon.orderservice.scene.order.OrderFragmentsListAdapter
import kr.evalon.orderservice.scene.order.OrderVm
import kr.evalon.orderservice.scene.order.cart.CartOrderItemVm
import kr.evalon.orderservice.scene.order.fragment.OrderFragment
import kr.evalon.orderservice.scene.order.option.OptionSelectPopup
import kr.evalon.orderservice.scene.order.option.OptionSelectPopupVm
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MainActivityTest  {

    @Test
    fun launchTest(){
        val count = CountDownLatch(1)
        ActivityScenario.launch(OrderActivity::class.java)
        count.await(10000L,TimeUnit.SECONDS)
    }

    @Test
    fun addTest(){
        val scenario = ActivityScenario.launch(OrderActivity::class.java)
        val c = CountDownLatch(1)
        scenario.onActivity {
            val vm = ViewModelProviders.of(it).get(OrderVm::class.java)
            vm.orderItemsLiveData.safeObserve { items->
                items ?: return@safeObserve
                if(!items.first().has(createDefault()))
                    throw Exception("Failed!")
                c.countDown()
            }
            vm.addItem(createDefault())
        }
        c.await()
        assert(c.count == 0L)
    }

    @Test
    fun optionTest(){
        val count = CountDownLatch(1)
        ItemListLiveData().safeObserve {
            val item = it.find { it.code == "00049" }
            requireNotNull(item)
            assert(item.options.isNotEmpty())
            count.countDown()
        }
        count.await()
    }

    @Test
    fun optionClickTest(){
        val scenario = ActivityScenario.launch(OrderActivity::class.java)
        val c = CountDownLatch(1)
        val categoryCode = "00003"
        val targetIndex = CategoryListLiveData().awaitValue().indexOfFirst {
            it.code == categoryCode
        }
        scenario.onActivity {
            it.viewPager.currentItem = targetIndex
        }
        c.await(1L,TimeUnit.SECONDS)
        scenario.onActivity {
            val f = it.supportFragmentManager.fragments.find {fr->
                fr.arguments?.getString(OrderFragment.CATEGORY_KEY) == categoryCode
            }
            assert(Looper.myLooper() != Looper.getMainLooper())
            requireNotNull(f)
            val items = (f as OrderFragment).getAdapter().getItems()
            assertEquals(1, items.size)
            items.first().clickItem()
        }
        c.await(3L,TimeUnit.SECONDS)
        scenario.onActivity {
            val f = it.supportFragmentManager.fragments.find {fr->
                fr.arguments?.getString(OrderFragment.CATEGORY_KEY) == categoryCode
            }
            val popup = f?.childFragmentManager?.fragments?.find { it is OptionSelectPopup }
            requireNotNull(popup)
            c.countDown()
        }
        c.await()
    }

    @Test
    fun optionPopupShow(){
        val scenario = ActivityScenario.launch(OrderActivity::class.java)
        val c = CountDownLatch(1)
        scenario.onActivity {
            val popup = OptionSelectPopup.newInstance("00049")
            popup.show(it.supportFragmentManager, popup.javaClass.canonicalName)
        }
        c.await()
    }

    @Test
    fun optionCreateAndShowCart(){
        val scenario = ActivityScenario.launch(OrderActivity::class.java)
        val c = CountDownLatch(1)
        createOption(scenario)
        c.await(1L,TimeUnit.SECONDS)
        createOption(scenario)
        c.await(1L,TimeUnit.SECONDS)
        scenario.onActivity {
            val vm = ViewModelProviders.of(it).get(OrderVm::class.java)
            vm.openCart()
        }
        c.await()
    }

    @Test
    fun optionCreateThird(){
        val scenario = ActivityScenario.launch(OrderActivity::class.java)
        val c = CountDownLatch(1)
        repeat(3){
            println("Test : $it")
            createOption(scenario)
            c.await(500L,TimeUnit.MILLISECONDS)
        }
        scenario.onActivity {
            val vm = ViewModelProviders.of(it).get(OrderVm::class.java)
            vm.openCart()
        }
        c.await()
    }

    @Test
    fun optionCreateDifferent(){
        val scenario = ActivityScenario.launch(OrderActivity::class.java)
        val c = CountDownLatch(1)
        val case1 = listOf(0,0,0)
        val case2 = listOf(0,1,0)
        repeat(3){
            createOption(scenario, if(it == 1) case2 else case1)
            c.await(500L,TimeUnit.MILLISECONDS)
        }
        scenario.onActivity {
            val vm = ViewModelProviders.of(it).get(OrderVm::class.java)
            vm.openCart()
        }
        c.await()
    }

    private fun createOption(scenario: ActivityScenario<OrderActivity>, selectIndex:List<Int> = listOf(0,0,0)) {
        val popup = OptionSelectPopup.newInstance("00049")
        val c = CountDownLatch(1)
        scenario.onActivity {
            popup.show(it.supportFragmentManager, popup.javaClass.canonicalName)
        }
        c.await(3L, TimeUnit.SECONDS)
        scenario.onActivity {
            val vm = ViewModelProviders.of(popup).get(OptionSelectPopupVm::class.java)
            vm.mainItemLiveData.observe(popup, Observer {
                vm.adapter.getHeaders().forEachIndexed { index,header ->
                    header.selectChangedLiveData.value = header.childVmList[selectIndex[index]]
                }
                vm.completeOptionSelect()
            })
        }
    }

    fun<T> LiveData<T>.safeObserve(func:(T)->Unit){
        Handler(Looper.getMainLooper()).post {
            observeForever(func)
        }
    }

    fun<T : Any> LiveData<T>.awaitValue():T{
        val c = CountDownLatch(1)
        var value : T? = null
        safeObserve {
            value = it
            c.countDown()
        }
        c.await()
        return requireNotNull(value)
    }
    fun createDefault() = OrderItem(code = "", name = "", price = 1000,
        categoryCodes = emptyList()).let { CartOrderItemVm(it,"") }
}