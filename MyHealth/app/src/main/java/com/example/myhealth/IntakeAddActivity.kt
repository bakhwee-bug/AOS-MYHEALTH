package com.example.myhealth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.example.myhealth.databinding.ActivityIntakeAddBinding
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory


class IntakeAddActivity : AppCompatActivity() {

    private val binding:ActivityIntakeAddBinding by lazy{ActivityIntakeAddBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.btn.setOnClickListener{SerchData()}
    }





/*
    private fun SerchData(){
        // 소프트 키보드 없애기
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        //hideSoftInputFromWindow의 매개변수로 포커스(토큰) 넣어줘야함 , flags : 즉시 0

        //1. Retrofit 생성
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.foodsafetykorea.go.kr/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //2. Retrofit이 해줄 작업에 대한 요구 명세 (인터페이스 설계 & 추상 메소드로 정의)
        // RetrofitService.kt 인터페이스 생성

        //3. RetrofitService 객체 생성
        val retrofitService:RetrofitService = retrofit.create(RetrofitService::class.java)

        //4. 원하는 작업 요청 하여 네트워크 작업 실행하는 객체 실행 리턴 받기
        val call:Call<FoodListResponse> = retrofitService.searchDataByJson(binding.et.text.toString())
        //5. 네트워크 작업 시작
        call.enqueue(object : Callback<FoodListResponse>{
            override fun onResponse(
                call: Call<FoodListResponse>,
                response: Response<FoodListResponse>
            ) {
                val foodResponse:FoodListResponse? = response.body()
                binding.recycler.adapter = MyAdapter(this@IntakeAddActivity, foodResponse!!.result)
            }

            override fun onFailure(call: Call<FoodListResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


    }*/
}