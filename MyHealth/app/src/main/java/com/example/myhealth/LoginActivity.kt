package com.example.myhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.twoneone.store/") // 서버의 기본 URL을 지정합니다.
            .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 팩토리를 추가합니다.
            .build()

        val loginService = retrofit.create(LoginService::class.java)

        /*회원가입 화면으로 이동*/
        btn_join.setOnClickListener{
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }


        /*로그인 버튼 클릭*/
        btn_login.setOnClickListener{
            var userName = edittext1.text.toString().trim() //trim: 문자열 공백 제거
            var pw = edittext2.text.toString().trim()
            val data = LoginRequest(userName, pw)

            /*빈칸이면 에러*/
            if(edittext1.text.isNullOrBlank()||edittext2.text.isNullOrBlank()){
                alertDialog =AlertDialog.Builder(this@LoginActivity).run {
                    setTitle("에러")
                    setMessage("이메일과 비밀번호를 모두 입력하세요.")
                    setNegativeButton("확인", null)
                    show()
                }
            }else{
                /*백엔드 통신*/
                //로그인 요청
                loginService.login(data).enqueue(object: Callback<ResponseData> {
                    /*통신 아예 X*/
                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                        Log.e("LOGIN", t.message.toString())
                        alertDialog = AlertDialog.Builder(this@LoginActivity).run {
                            setTitle("에러")
                            setMessage(t.message)
                            setNegativeButton("확인", null)
                            show()
                        }
                    }
                    /*통신O*/
                    override fun onResponse(
                        call: Call<ResponseData>, response: Response<ResponseData>
                    ) {
                        if(response.isSuccessful) {
                            val login = response.body()
                            Log.d("LOGIN", "code : " + login?.code)
                            Log.d("LOGIN", "httpStatus : " + login?.httpStatus)
                            Log.d("LOGIN", "message : " + login?.message)
                            Log.d("LOGIN", "data : " + login?.data)

                            val token = response.headers().values("Set-Cookie").toString()
                            Log.d("LOGIN", "token : " + token)


                            //유저 정보 가져오기
                            val userservice : InfoActivity = retrofit.create(InfoActivity::class.java)
                            Log.e("Login:userservice", "이건 되나..")

                            userservice.requestUser(token).enqueue(object : Callback<User>{
                                override fun onResponse(call: Call<User>, response: Response<User>) {
                                    if(response.isSuccessful){
                                        //정상적으로 통신이 된 경우
                                        Log.e("Login:onResponse", "유저서비스의 리퀘스트유저")
                                        val user = response.body()
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                                            putExtra("token", token)
                                            putExtra("user_name", user?.userName)

                                        }

                                        alertDialog =AlertDialog.Builder(this@LoginActivity).run {
                                            setTitle(login!!.code)
                                            setMessage(login?.message)
                                            setPositiveButton("확인"){dialog, which->
                                                dialog.dismiss()
                                                finishAffinity()
                                                startActivity(intent)
                                            }
                                            show()
                                        }
                                    }
                                    else {
                                        //통신 실패
                                        try {
                                            val body = response.errorBody()!!.string()
                                            Log.e("Login:User", "error - body : $body")
                                        } catch (e: IOException) {
                                            e.printStackTrace()
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<User>, t: Throwable) {
                                    //통신 실패
                                    Log.d("Login: User","에러: "+t.message.toString())
                                    Log.d("message: ", t.message.toString())
                                }
                            })

                        }else{
                            Log.d("LOGIN", "result : 아이디 또는 비밀번호가 잘못되었습니다.")
                            when(response.code()){
                                400-> Toast.makeText(this@LoginActivity, "요청 url 파라미터 오류",Toast.LENGTH_LONG).show()
                                401-> Toast.makeText(this@LoginActivity, "패스워드 오류",Toast.LENGTH_LONG).show()
                                404-> Toast.makeText(this@LoginActivity, "존재하지 않는 아이디",Toast.LENGTH_LONG).show()
                            }
                        }

                    }

                 })




                }
            }


    }
}