package com.example.myhealth

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

object RetrofitClient {

    private var instance: Retrofit? = null

    fun getInstance() : Retrofit {
        if(instance == null){
            var builder = OkHttpClient().newBuilder()


            var okHttpClient = builder
                .cookieJar(JavaNetCookieJar(CookieManager()))
                .build()

            instance = Retrofit.Builder()
                .baseUrl("https://www.twoneone.store/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}