package com.example.myhealth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealth.databinding.ItemRecyclerAddBinding

class MyAdapter(var context: Context, var items:MutableList<FoodData>) : RecyclerView.Adapter<MyAdapter.VH>(){
    //뷰홀더는 아이템뷰 하나짜리 받도록 되어 있음 그래서 binding.root 를 줘야됨
    inner class VH(var binding: ItemRecyclerAddBinding) : RecyclerView.ViewHolder(binding.root){
    }
    //inflate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRecyclerAddBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //holder에 연결
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:FoodData = items[position] //배열처럼 쓰는걸 권장

        holder.binding.foodTitle.text = item.descKor
        holder.binding.foodGram.text = "${item.servingSize}g"
        holder.binding.foodKcal.text = "${item.nutrCont1}kcal"

/*        //링크 열기
        holder.binding.root.setOnClickListener {
            //핸드폰 디바이스의 인터넷 앱을 실행
            val intent = Intent(Intent.ACTION_VIEW) //인터넷을 보여주는 것
            intent.data = Uri.parse(item.link)
            context.startActivity(intent)
        }*/
    }

    }