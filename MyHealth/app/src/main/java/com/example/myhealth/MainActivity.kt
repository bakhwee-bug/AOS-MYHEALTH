package com.example.myhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_main.*


import java.io.IOException


var token : String? = ""

class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = intent.getStringExtra("token")
        var retrofit: Retrofit = RetrofitClient.getInstance()
        val userservice : TokenActivity = retrofit.create(TokenActivity::class.java)

        val BearerToken = "Bearer $token"
        val callInbody = userservice.requestInBody(BearerToken)
        val callIntake = userservice.requestIntake(BearerToken, "2023-12-25")
        val callExercise = userservice.requestExercise(BearerToken, "2023-12-25")
        //인바디 조회
        callInbody.enqueue(object : Callback<ResponseInbody>{
            override fun onResponse(call: Call<ResponseInbody>, response: Response<ResponseInbody>) {
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callInbody", apiResponse.message)
                            var inbody = apiResponse?.data
                            summary_bmi_num.text = inbody?.bmi.toString()
                            summary_skeletalMuscle_num.text = inbody?.bmi.toString()
                            summary_fatPer_num.text = inbody?.bmi.toString()
                            some_id.text = inbody?.user?.nickName.toString() + "님"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseInbody>, t: Throwable) {
                Toast.makeText(this@MainActivity, "통신 실패", Toast.LENGTH_SHORT).show()
            }

        })
        //일일 섭취량 조회
        callIntake.enqueue(object : Callback<ResponseIntake>{
            override fun onResponse(call: Call<ResponseIntake>, response: Response<ResponseIntake>
            ) {
                Log.d("MainActivity_callIntake", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callIntake", apiResponse.message)
                            var intake = apiResponse?.data
                            //오늘 섭취 칼로리
                            today_intake.text = intake?.currentCalorie.toString()
                            //목표 칼로리
                            target_intake.text = intake?.targetCalorie.toString()
                        }
                        400->{
                            Log.d("MainActivity_callIntake", apiResponse.message)
                        }
                    }


                }else{
                    //통신 실패
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("Login:User", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<ResponseIntake>, t: Throwable) {
                Log.d("MainActivity_callIntake", t.message.toString())
            }
        })
        //일일 운동 조회
        callExercise.enqueue(object : Callback<ResponseExercise>{
            override fun onResponse(call: Call<ResponseExercise>, response: Response<ResponseExercise>
            ) {
                Log.d("MainActivity_callExercise", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callExercise", apiResponse.message)
                            var exercise = apiResponse?.data
                            //오늘 소모칼로리
                            today_exercise.text = exercise?.currentCalorie.toString()
                            //목표 칼로리
                            target_exercise.text = exercise?.targetCalorie.toString()
                        }
                        400->{
                            Log.d("MainActivity_callExercise", apiResponse.message)
                        }
                    }
            }else{
                    //통신 실패
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("Login:User", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }

            override fun onFailure(call: Call<ResponseExercise>, t: Throwable) {
                Log.d("MainActivity_callExercise", t.message.toString())
            }
        })

        setContentView(R.layout.activity_main)

        //인바디 블록 누르면 입력 다이얼로그 생성
        btn_inbody.setOnClickListener{
            showInbodyDialog()
        }

        //식단기록 블록 누르면 Record 페이지로 이동
        btn_food.setOnClickListener{
            val intent = Intent(this@MainActivity, IntakeRecordActivity::class.java).apply {
                putExtra("BearerToken", BearerToken)
            }
            startActivity(intent)
        }
        //운동기록 블록 누르면 입력 다이얼로그 생성
        btn_exercise.setOnClickListener{
            showExerciseDialog()
        }

        //앱바 누르면 마이페이지로 이동
        appBarLayout.setOnClickListener{

        }


        /*setSupportActionBar(findViewById(R.id.appBarLayout))*/

    }

    private fun showExerciseDialog() {
        // 다이얼로그를 만들기 위한 빌더를 생성합니다.
        val builder = AlertDialog.Builder(this)

        // 레이아웃 인플레이터를 가져옵니다.
        val inflater = layoutInflater

        // dialog_exercise_add.xml 파일을 인플레이트해서 뷰를 만듭니다.
        val exerciseView = inflater.inflate(R.layout.dialog_exercise_add, null)

        // 빌더에 뷰를 설정합니다.
        builder.setView(exerciseView)

        // 뷰에서 위젯을 찾습니다.
        val editText1 = exerciseView.findViewById<EditText>(R.id.editText1)
        val editText2 = exerciseView.findViewById<EditText>(R.id.editText2)
        val editText3 = exerciseView.findViewById<EditText>(R.id.editText3)
        val buttonSubmit = exerciseView.findViewById<Button>(R.id.buttonSubmit)

        // 다이얼로그를 만들고 보여줍니다.
        val dialog = builder.create()
        dialog.show()

        // 제출 버튼에 클릭 리스너를 설정합니다.
        buttonSubmit.setOnClickListener {
            val inputDate = editText1.text.toString()
            val inputExerciseName = editText2.text.toString()
            val inputCalorie1 = editText3.text.toString()
           // val inputCalorie = inputCalorie1.toDouble()

            // 입력값을 처리합니다.

            // 다이얼로그를 닫습니다.
            dialog.dismiss()

        }
    }

    private fun showInbodyDialog() {
        // 다이얼로그를 만들기 위한 빌더를 생성합니다.
        val builder = AlertDialog.Builder(this)

        // 레이아웃 인플레이터를 가져옵니다.
        val inflater = layoutInflater

        // dialog_inbody_add.xml 파일을 인플레이트해서 뷰를 만듭니다.
        val inbodyView = inflater.inflate(R.layout.dialog_inbody_add, null)

        // 빌더에 뷰를 설정합니다.
        builder.setView(inbodyView)

        // 뷰에서 위젯을 찾습니다.
        val editText1 = inbodyView.findViewById<EditText>(R.id.editText1)
        val editText2 = inbodyView.findViewById<EditText>(R.id.editText2)
        val editText3 = inbodyView.findViewById<EditText>(R.id.editText3)
        val buttonSubmit = inbodyView.findViewById<Button>(R.id.buttonSubmit)

        // 다이얼로그를 만들고 보여줍니다.
        val dialog = builder.create()
        dialog.show()

        // 제출 버튼에 클릭 리스너를 설정합니다.
        buttonSubmit.setOnClickListener {
            val input1 = editText1.text.toString()
            val input2 = editText2.text.toString()
            val input3 = editText3.text.toString()

            // 입력값을 처리합니다.
            // ...

            // 다이얼로그를 닫습니다.
            dialog.dismiss()

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (doubleBackToExitPressedOnce) {
                finish()
                return true
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "뒤로 버튼을 한 번 더 누르시면 로그아웃됩니다", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 2000)
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onStart() {
        super.onStart()
        token = intent.getStringExtra("token")
        var retrofit: Retrofit = RetrofitClient.getInstance()
        val userservice : TokenActivity = retrofit.create(TokenActivity::class.java)
        val BearerToken = "Bearer $token"
        val callInbody = userservice.requestInBody(BearerToken)
        val callIntake = userservice.requestIntake(BearerToken, "2023-12-25")
        val callExercise = userservice.requestExercise(BearerToken, "2023-12-25")
        //인바디 조회
        callInbody.enqueue(object : Callback<ResponseInbody>{
            override fun onResponse(call: Call<ResponseInbody>, response: Response<ResponseInbody>) {
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callInbody", apiResponse.message)
                            var inbody = apiResponse?.data
                            summary_bmi_num.text = inbody?.bmi.toString()
                            summary_skeletalMuscle_num.text = inbody?.bmi.toString()
                            summary_fatPer_num.text = inbody?.bmi.toString()
                            some_id.text = inbody?.user?.nickName.toString() + "님"
                        }
                    }


                }
            }

            override fun onFailure(call: Call<ResponseInbody>, t: Throwable) {
                Toast.makeText(this@MainActivity, "통신 실패", Toast.LENGTH_SHORT).show()
            }

        })
        //일일 섭취량 조회
        callIntake.enqueue(object : Callback<ResponseIntake>{
            override fun onResponse(call: Call<ResponseIntake>, response: Response<ResponseIntake>
            ) {
                Log.d("MainActivity_callIntake", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callIntake", apiResponse.message)
                            var intake = apiResponse?.data
                            //오늘 섭취 칼로리
                            today_intake.text = intake?.currentCalorie.toString()
                            //목표 칼로리
                            target_intake.text = intake?.targetCalorie.toString()
                        }
                        400->{
                            Log.d("MainActivity_callIntake", apiResponse.message)
                        }
                    }


                }else{
                    //통신 실패
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("Login:User", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<ResponseIntake>, t: Throwable) {
                Log.d("MainActivity_callIntake", t.message.toString())
            }
        })
        //일일 운동 조회
        callExercise.enqueue(object : Callback<ResponseExercise>{
            override fun onResponse(call: Call<ResponseExercise>, response: Response<ResponseExercise>
            ) {
                Log.d("MainActivity_callExercise", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callExercise", apiResponse.message)
                            var exercise = apiResponse?.data
                            //오늘 소모칼로리
                            today_exercise.text = exercise?.currentCalorie.toString()
                            //목표 칼로리
                            target_exercise.text = exercise?.targetCalorie.toString()
                        }
                        400->{
                            Log.d("MainActivity_callExercise", apiResponse.message)
                        }
                    }
                }else{
                    //통신 실패
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("Login:User", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }

            override fun onFailure(call: Call<ResponseExercise>, t: Throwable) {
                Log.d("MainActivity_callExercise", t.message.toString())
            }
        })
    }

}