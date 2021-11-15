package com.example.uxdesign.ui.theme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uxdesign.databinding.SearchRowBinding
import com.example.uxdesign.model.data.Place


class ThemeRecyclerViewAdapter() :
    RecyclerView.Adapter<ThemeRecyclerViewAdapter.ViewHolder>() {
    private var items:List<Place>? = null
    inner class ViewHolder(val binding: SearchRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let {
            holder.binding.apply {
                placeName.text = it[position].place_name
                addressName.text = it[position].address_name
                categoryName.text = it[position].category_name
            }
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun submitList(list: List<Place>?){
        items = list
        notifyDataSetChanged()
    }
}