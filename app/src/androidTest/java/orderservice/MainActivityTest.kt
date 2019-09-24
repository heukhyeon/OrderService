package orderservice

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.test.core.app.ActivityScenario
import androidx.test.runner.AndroidJUnit4
import kr.evalon.orderservice.models.MenuItem
import kr.evalon.orderservice.models.OrderItem
import kr.evalon.orderservice.scene.order.OrderActivity
import kr.evalon.orderservice.scene.order.OrderVm
import kr.evalon.orderservice.scene.order.cart.CartOrderItemVm
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
        count.await(10L,TimeUnit.SECONDS)
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

    fun<T> LiveData<T>.safeObserve(func:(T)->Unit){
        Handler(Looper.getMainLooper()).post {
            observeForever(func)
        }
    }

    fun createDefault() = OrderItem(code = "", name = "", price = 1000,
        categoryCodes = emptyList()).let { CartOrderItemVm(it,"") }
}