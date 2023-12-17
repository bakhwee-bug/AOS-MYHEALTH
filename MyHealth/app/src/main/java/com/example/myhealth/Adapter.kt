package com.example.myhealth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealth.databinding.ItemRecyclerAddBinding
import com.example.myhealth.databinding.ItemRecyclerBinding

//음식 추가할 때
class MyAdapter(var context: Context, var items:MutableList<FoodData>) : RecyclerView.Adapter<MyAdapter.VH>(){
    //뷰홀더는 아이템뷰 하나짜리 받도록 되어 있음 그래서 binding.root 를 줘야됨
    inner class VH(var binding: ItemRecyclerAddBinding) : RecyclerView.ViewHolder(binding.root){
    }
    //아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRecyclerAddBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    //리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return items.size
    }

    //View에 내용 입력
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:FoodData = items[position] //배열처럼 쓰는걸 권장
        holder.binding.foodTitle.text = item.descKor
        holder.binding.foodGram.text = "${item.servingSize}g"
        holder.binding.foodKcal.text = "${item.nutrCont1}kcal"
        //(1)리스트 내 항목 클릭 시 onClick호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }
    // (2) 리스너 인터페이스
    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    //(4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    }

//음식 삭제할 때
class MyAdapter2(var context: Context, var items: MutableList<ResponseIntake.Calorie.Intake>) : RecyclerView.Adapter<MyAdapter2.VH>(){
    inner class VH(var binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root){}
    //inflate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter2.VH {
        return VH(ItemRecyclerBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:ResponseIntake.Calorie.Intake=items[position]
        holder.binding.foodTitle.text=item.content
        holder.binding.foodKcal.text="${item.calorie.toString()}kcal"
    }


}