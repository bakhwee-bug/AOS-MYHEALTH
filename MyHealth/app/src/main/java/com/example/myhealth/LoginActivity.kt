package com.example.myhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://example.com/") // 서버의 기본 URL을 지정합니다.
            .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 팩토리를 추가합니다.
            .build()

        val apiService = retrofit.create(LoginService::class.java)

        val request = LoginRequest("user1", "pw1")
        try {
            val response = apiService.login(request)
            // 응답 처리
            val code = response.code
            val message = response.message
            val token = response.data.token
        } catch (e: Exception) {
            // 예외 처리
            e.printStackTrace()
        }



    }
}