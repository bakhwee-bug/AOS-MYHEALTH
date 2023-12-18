package com.example.myhealth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.Toast
import com.example.myhealth.databinding.ActivityIntakeAddBinding
import com.google.gson.Gson
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
        //토큰 받아오기
        token = intent.getStringExtra("token")
        val BearerToken = "Bearer $token"
        setContentView(binding.root)
        Log.d("onCreate토큰", BearerToken)


        //검색 버튼 누르면 음식 찾기
        binding.btn.setOnClickListener{
            val searchFoodName = inputFoodName.getText().toString()
            Log.e("IntakeAddActivity", searchFoodName)
            searchPublicApi2(searchFoodName, BearerToken)}

    }



    fun searchPublicApi2(productName: String, BearerToken:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.foodsafetykorea.go.kr/") // 기본 URL
            .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱 라이브러리 설정
            .build()

        val retrofit2 = Retrofit.Builder()
            .baseUrl("https://www.twoneone.store/") // 서버의 기본 URL을 지정합니다.
            .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 팩토리를 추가합니다.
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
                        val listAdapter = MyAdapter(this@IntakeAddActivity, data!!.i2790.row)
                        binding.recycler.adapter = listAdapter
                            // result.text=data.toString()
                        Log.e("IntakeAddActivity", "값 불러오기 성공")
                        Log.e("IntakeAddActivity","아이템 개수 : ${data?.i2790?.row?.size}")
                        //음식 터치 시 기록
                        listAdapter.setItemClickListener(object: MyAdapter.OnItemClickListener{
                            override fun onClick(v: View, position: Int) {
                                //객체에 넣기
                                val eatFoodName = data?.i2790?.row?.get(position)?.descKor.toString()
                                val eatFoodCal = data?.i2790?.row?.get(position)?.nutrCont1!!.toDouble()
                                val eatFoodDate = daily
                                val dateContentCal = DaConCal(
                                    date = eatFoodDate,
                                    content = eatFoodName,
                                    calorie = eatFoodCal
                                )
                                Log.d("IntakeAddActivity_onClick", dateContentCal.toString())
                                //POST요청(식품을 내 기록에 추가)
                                val service2 = retrofit2.create(TokenActivity::class.java)
                                service2.eatFood(BearerToken, dateContentCal).enqueue(object : Callback<ResponseData>{
                                    override fun onResponse(
                                        call: Call<ResponseData>, response: Response<ResponseData>
                                    ) {
                                        Log.d("service2토큰", BearerToken)
                                        if(response.isSuccessful) {
                                            Log.d("IntakeAddActivity_callIntake", "추가 성공")
                                            Toast.makeText(
                                                binding.root.context,
                                                "${eatFoodName} 추가했습니다", Toast.LENGTH_SHORT
                                            ).show()
                                        }else{    // 에러 응답 처리
                                            val errorBody = response.errorBody()
                                            if (errorBody != null) {
                                                Log.d("IntakeAddActivity_errorBody", response.raw().toString())
                                            } else {
                                                // 에러 응답이 null인 경우 처리
                                            }

                                        }
                                    }
                                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                                        Log.d("IntakeAddActivity_onFailure", t.message.toString())
                                    }
                                 })
                            }
                        })
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