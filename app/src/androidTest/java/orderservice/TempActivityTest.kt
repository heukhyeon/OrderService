package orderservice

import androidx.test.core.app.ActivityScenario
import androidx.test.runner.AndroidJUnit4
import kr.evalon.orderservice.scene.temp.TempActivity
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class TempActivityTest {

    @Test
    fun test(){
        ActivityScenario.launch(TempActivity::class.java)
        CountDownLatch(1).await()
    }
}