package com.example.myhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.twoneone.store") // 서버의 기본 URL을 지정합니다.
        .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 팩토리를 추가합니다.
        .build()

    val loginService = retrofit.create(LoginService::class.java)

}