package com.example.myhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myhealth.databinding.ActivityIntakeRecordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.time.LocalDate


class IntakeRecordActivity : AppCompatActivity() {
    private val binding: ActivityIntakeRecordBinding by lazy{ ActivityIntakeRecordBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = intent.getStringExtra("token")
        val BearerToken = "Bearer $token"
        setContentView(binding.root)
        Log.d("MainActivity_callIntake", BearerToken)

        //fab버튼 누르면 add 페이지로 이동
        binding.fab.setOnClickListener{
            val intent = Intent(this@IntakeRecordActivity, IntakeAddActivity::class.java).apply {
                putExtra("token", token)
            }
            startActivity(intent)
        }
    }

    //음식 Add 하고 화면으로 넘어오면 음식들 다시 출력
    override fun onStart() {
        super.onStart()
        token = intent.getStringExtra("token")
        val BearerToken = "Bearer $token"
        var retrofit: Retrofit = RetrofitClient.getInstance()
        val userservice : TokenActivity = retrofit.create(TokenActivity::class.java)
        val callIntake = userservice.requestIntake(BearerToken, daily)
        //일일 섭취량 조회
        callIntake.enqueue(object : Callback<ResponseIntake> {
            override fun onResponse(call: Call<ResponseIntake>, response: Response<ResponseIntake>
            ) {
                Log.d("IntakeRecordActivity_callIntake", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("IntakeRecordActivity_callIntake", apiResponse.message)
                            Log.d("IntakeRecordActivity_callIntake", apiResponse.data.toString())
                            var intake = apiResponse?.data?.intakes
                            val listAdapter = intake?.let {
                                MyAdapter2(this@IntakeRecordActivity,
                                    it
                                )
                            }
                            binding.myRecyclerView.adapter =listAdapter
                            listAdapter?.setItemClickListener(object : MyAdapter2.OnItemClickListener{
                                override fun onClick(v: View, position: Int) {
                                    Log.d("블ㄹ록 선택", "삭제시작")
                                    val deleteFood = userservice.deleteIntakeTask(BearerToken,
                                        intake?.get(position)!!.id
                                    )
                                    deleteFood.enqueue(object : Callback<DelFood>{
                                        override fun onResponse(call: Call<DelFood>, response: Response<DelFood>
                                        ) {
                                            Toast.makeText(binding.root.context, "삭제했습니다", Toast.LENGTH_SHORT).show()
                                            Log.d("IntakeRecoedActivity_errorBody", response.raw().toString())
                                            //화면 refresh
                                            finish()
                                            startActivity(intent)
                                        }

                                        override fun onFailure(call: Call<DelFood>, t: Throwable) {
                                            Toast.makeText(binding.root.context, "삭제 실패", Toast.LENGTH_SHORT).show()                                        }
                                    })
                                }
                            })

                            Log.e("IntakeRecordActivity_callIntake", "값 불러오기 성공")
                        }
                        400->{
                            Log.d("IntakeRecordActivity_callIntake", apiResponse.message)
                        }
                    }
                }else{
                    //통신 실패
                    try {
                        if (response.errorBody() != null) {
                            val body = response.errorBody()!!.string()
                            Log.e("IntakeRecordActivity_callIntake", "error - body: $body")
                        } else {
                            Log.e("IntakeRecordActivity_callIntake", "error - empty error body")
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<ResponseIntake>, t: Throwable) {
                Log.d("IntakeRecordActivity_callIntake", t.message.toString())
            }
        })
    }
}