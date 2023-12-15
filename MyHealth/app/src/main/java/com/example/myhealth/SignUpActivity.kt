package com.example.myhealth

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.myhealth.databinding.ActivitySignUpBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.twoneone.store") // 서버의 기본 URL을 지정합니다.
            .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 팩토리를 추가합니다.
            .build()

        val SignUpService = retrofit.create(SignUpService::class.java)

        /*회원가입 버튼 클릭*/
        var btn_signup = binding.btnSignup
        btn_signup.setOnClickListener{
            Toast.makeText(this, "Button", Toast.LENGTH_LONG).show()
        }

    }







}