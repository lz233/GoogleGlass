package moe.lz233.googleglass.cloudmusic.logic.network

import moe.lz233.googleglass.cloudmusic.BuildConfig
import moe.lz233.googleglass.cloudmusic.logic.network.interceptor.HttpLogger
import moe.lz233.googleglass.cloudmusic.logic.network.interceptor.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {
    const val BASE_HOST = "music.163.com"
    const val BASE_URL = "https://$BASE_HOST"

    private val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor(HttpLogger()).apply {
                if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(RequestInterceptor())
            .retryOnConnectionFailure(true)
            .callTimeout(20, TimeUnit.SECONDS)
            .build()

    val exoPlayerOkHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(moe.lz233.googleglass.cloudmusic.logic.network.interceptor.ExoPlayerInterceptor())
            .retryOnConnectionFailure(true)
            .callTimeout(20, TimeUnit.SECONDS)
            .build()


    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}