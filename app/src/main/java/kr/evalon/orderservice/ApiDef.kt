package kr.evalon.orderservice

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object Login {
    class Request  constructor(@SuppressLint("HardwareIds") val key:String = Build.SERIAL)
    class Response(val code:Int,
                   val message:String,
                   val sessionKey:String,
                   val storeName:String)
}


interface Api {
    @POST("/login")
    fun login(@Body request:Login.Request): Single<Login.Response>

    companion object : KoinComponent{
        private var sessionKey = ""
        fun saveSession(key:String){
            sessionKey = key
        }
        fun getRestInstance(context: Context): Api {
            val url = context.getString(R.string.api_url)
            return getRestInstance(url)
        }

        fun getRestInstance(url: String): Api {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("sessionKey", sessionKey).build()
                chain.proceed(request)
            }
            return Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}
