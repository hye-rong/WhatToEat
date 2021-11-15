package com.example.uxdesign.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uxdesign.databinding.FavoriteRowBinding
import com.example.uxdesign.model.data.Favorite

class FavoriteRecyclerAdapter(val items: ArrayList<Favorite>): RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(url:String)
    }

    var itemClickListener:OnItemClickListener?=null
    inner class ViewHolder(val binding: FavoriteRowBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition].url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FavoriteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            nameView.text = items[position].fName
            scoreView.text = String.format("%.1f", items[position].fScore)
            themeView.text = items[position].fTheme

            imageView1.setImageResource(items[position].id1)
            imageView2.setImageResource(items[position].id2)
            if(items[position].id3 != 0)
                imageView3.setImageResource(items[position].id3)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}