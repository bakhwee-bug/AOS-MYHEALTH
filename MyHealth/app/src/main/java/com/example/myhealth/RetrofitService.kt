package com.example.myhealth

import androidx.databinding.library.BuildConfig
import com.example.myhealth.BuildConfig.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {
    @GET("/I2790/json/1/1000/DESC_KOR=")
    fun searchDataByJson(@Query("query") query:String):Call<FoodListResponse>
}