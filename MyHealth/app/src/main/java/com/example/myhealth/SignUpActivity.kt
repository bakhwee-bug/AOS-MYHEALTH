package com.example.myhealth

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.myhealth.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.edittext1
import kotlinx.android.synthetic.main.activity_sign_up.edittext2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignUpActivity : AppCompatActivity() {
    private var alertDialog: AlertDialog? = null

/*
    private lateinit var binding: ActivitySignUpBinding
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val eventHandler = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1== DialogInterface.BUTTON_POSITIVE){
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                }
            }
        }
/*
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
*/

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.twoneone.store") // 서버의 기본 URL을 지정합니다.
            .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 팩토리를 추가합니다.
            .build()

        val SignUpService = retrofit.create(SignUpService::class.java)

        /*회원가입 버튼 클릭*/
        btn_signup.setOnClickListener{
            var userName = edittext1.text.toString().trim() //trim: 문자열 공백 제거
            var pw = edittext2.text.toString().trim()
            var nickname = edittext3.text.toString()
            val data = User(userName, pw,nickname)

            /*백엔드 통신*/
            SignUpService.requestRegister(data).enqueue(object: Callback<ResponseData> {
                /*통신 성공 시*/
                override fun onResponse(
                    call: Call<ResponseData>, response: Response<ResponseData>
                ) {
                    when(response.code()){
                        200->{
                            val login = response.body()
                            Log.d("LOGIN", "code : " + login?.code)
                            Log.d("LOGIN", "httpStatus : " + login?.httpStatus)
                            Log.d("LOGIN", "message : " + login?.message)
                            Log.d("LOGIN", "data : " + login?.data)

                            AlertDialog.Builder(this@SignUpActivity).run {
                                setTitle(login?.httpStatus)
                                setMessage(login?.message)
                                setPositiveButton("확인", eventHandler)
                                show()
                            }
                        }
                        400 -> { Toast.makeText(this@SignUpActivity, "요청 url 파라미터 오류",Toast.LENGTH_LONG).show() }

                    }
                }
                /*통신 실패 시*/
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.e("LOGIN", t.message.toString())
                    alertDialog = AlertDialog.Builder(this@SignUpActivity).run {
                        setTitle("에러")
                        setMessage(t.message)
                        setNegativeButton("확인", null)
                        show()
                    }
                }
            })
        }



    }







}