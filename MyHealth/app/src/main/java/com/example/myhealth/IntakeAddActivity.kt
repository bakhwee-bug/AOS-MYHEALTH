package com.example.myhealth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.Toast
import com.example.myhealth.databinding.ActivityIntakeAddBinding
import kotlinx.android.synthetic.main.activity_intake_add.*
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class IntakeAddActivity : AppCompatActivity() {

    private val binding:ActivityIntakeAddBinding by lazy{ActivityIntakeAddBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.btn.setOnClickListener{
            val searchFoodName = inputFoodName.getText().toString()
            Log.e("IntakeAddActivity", searchFoodName)
            searchPublicApi2(searchFoodName)}
    }

    fun searchPublicApi(productName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.foodsafetykorea.go.kr/") // 기본 URL
            .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱 라이브러리 설정
            .build()

        val service = retrofit.create(PublicApiService::class.java)

        service.searchByProductName("8152179687f14444a57d", productName) // API 키와 품목명을 입력
            .enqueue(object : Callback<PublicApiResponce> {
                override fun onResponse(
                    call: Call<PublicApiResponce>,
                    response: Response<PublicApiResponce>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        // 성공적으로 데이터를 받았을 때의 처리를 작성
                        result.text=data.toString()
                        Log.e("IntakeAddActivity", "값 불러오기 성공")

                    }else{
                        //통신 실패
                        try {
                            val body = response.errorBody()!!.string()
                            Log.e("IntakeAddActivity", "error - body : $body")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<PublicApiResponce>, t: Throwable) {
                    // 요청이 실패했을 때의 처리를 작성해주세요.
                    Log.d("MainActivity_callInbody", "연결 실패")

                }
            })
    }
    fun searchPublicApi2(productName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.foodsafetykorea.go.kr/") // 기본 URL
            .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱 라이브러리 설정
            .build()

        val service = retrofit.create(PublicApiService::class.java)

        service.searchByProductName("8152179687f14444a57d", productName) // API 키와 품목명을 입력
            .enqueue(object : Callback<PublicApiResponce> {
                override fun onResponse(
                    call: Call<PublicApiResponce>,
                    response: Response<PublicApiResponce>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        // 성공적으로 데이터를 받았을 때의 처리를 작성
                        binding.recycler.adapter = MyAdapter(this@IntakeAddActivity, data!!.i2790.row)
                            // result.text=data.toString()
                        Log.e("IntakeAddActivity", "값 불러오기 성공")
                        Log.e("IntakeAddActivity","아이템 개수 : ${data?.i2790?.row?.size}")

                    }else{
                        //통신 실패
                        try {
                            val body = response.errorBody()!!.string()
                            Log.e("IntakeAddActivity", "error - body : $body")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<PublicApiResponce>, t: Throwable) {
                    // 요청이 실패했을 때의 처리를 작성해주세요.
                    Log.d("MainActivity_callInbody", "연결 실패")

                }
            })
    }


}