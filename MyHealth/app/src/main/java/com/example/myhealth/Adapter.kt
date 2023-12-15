package com.example.myhealth

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
/*
import com.example.myhealth.databinding.ItemRecyclerBinding
*/




/*
class MyAdapter(var context: Context, var items:List<FoodDetailResponse>):Adapter<MyAdapter.VH>(){
    //뷰홀더는 아이템뷰 하나짜리 받도록 되어 있음 그래서 binding.root 를 줘야됨
    inner class VH(var binding: ItemRecyclerBinding) : ViewHolder(binding.root){
    }

    //inflate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRecyclerBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    //holder에 연결
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:FoodDetailResponse = items[position]

        var title:String = HtmlCompat.fromHtml(item.DESC_KOR).toString()
        holder.binding.food_title.text = title

    }

}*/
