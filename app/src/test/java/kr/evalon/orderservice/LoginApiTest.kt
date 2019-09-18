package kr.evalon.orderservice

import android.content.Context
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LoginApiTest {
    private val api by lazy {
        Api.getRestInstance(URL)
    }


    @Test
    fun invalidLogin(){
        val observer = TestObserver<Login.Response>()
        api.login(Login.Request("asfafww"))
            .subscribe(observer)
        observer.await()
        observer.assertComplete()
    }
}