package com.example.myhealth

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealth.databinding.ItemRecyclerAddBinding

class MyAdapter(private val dataList: ArrayList<FoodData>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    val TAG = "MyAdapter"

    @SuppressLint("NotifyDataSetChanged")
    fun updateFood(newFood : List<FoodData>){
        dataList.clear()
        dataList.addAll(newFood)
        notifyDataSetChanged()
    }
    //생성된 뷰 홀더에 값 정
    class ViewHolder(val binding: ItemRecyclerAddBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(currentFood: FoodData){
                binding.foodTitle.text=currentFood.descKor
            }
        }
    //어떤 xml으로 뷰홀더 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerAddBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    //뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = dataList
        if(food!=null){
            holder.bind(food.get(position))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}