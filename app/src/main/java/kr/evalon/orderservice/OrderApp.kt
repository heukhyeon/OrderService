package kr.evalon.orderservice

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import kr.evalon.orderservice.models.USER_KEY
import org.koin.core.context.startKoin
import org.koin.dsl.module

class OrderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this, FirebaseOptions.Builder()
            .setProjectId(getString(R.string.project_id))
            .setApplicationId("kr.evalon.ordeservice")
            .setDatabaseUrl(getString(R.string.database_url))
            .setApiKey(getString(R.string.firebase_api_key))
            .build())
        startKoin {
            modules(listOf(module {
                single { (key:String)-> if(key == USER_KEY) "tester" else throw IllegalArgumentException() }
            }))
        }
    }
}