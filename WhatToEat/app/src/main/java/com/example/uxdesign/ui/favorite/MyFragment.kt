package com.example.uxdesign.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat.VERTICAL
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uxdesign.R
import com.example.uxdesign.databinding.FragmentMyBinding
import com.example.uxdesign.model.data.Favorite
import com.example.uxdesign.ui.search.WebViewActivity

class MyFragment : Fragment() {
    lateinit var binding: FragmentMyBinding
    lateinit var adapter:FavoriteRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("fragmenttest","onCreateView")
        binding = FragmentMyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val items = ArrayList<Favorite>(4)
        items.add(Favorite("요코스카 쓰나미", ".", "일식당", 4.5f,
        R.drawable.f11, R.drawable.f12, R.drawable.f13, "https://m.place.naver.com/restaurant/1110008678/home?entry=plt"))
        items.add(Favorite("코블러", ".", "칵테일바", 4.0f,
            R.drawable.f21, R.drawable.f22, 0, "https://m.place.naver.com/restaurant/1578448821/home"))
        items.add(Favorite("이치에", ".", "일식당", 5.0f,
            R.drawable.f31, R.drawable.f32, R.drawable.f33, "https://m.place.naver.com/restaurant/21656860/home?entry=plt"))
        items.add(Favorite("오스테리아 오르조", ".", "이탈리아 음식점", 5.0f,
            R.drawable.f41, R.drawable.f42, R.drawable.f43, "https://m.place.naver.com/restaurant/1229610281/home?entry=plt"))
        items.add(Favorite("심야식당 기억", ".", "일식당", 4.3f,
            R.drawable.f51, R.drawable.f52, 0, "https://m.place.naver.com/restaurant/1601566650/home"))

        val decoration = DividerItemDecoration(requireContext(), VERTICAL)

        adapter = FavoriteRecyclerAdapter(items)
        adapter.itemClickListener = object : FavoriteRecyclerAdapter.OnItemClickListener{
            override fun OnItemClick(url: String) {
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        }
        binding.recyclerView.apply {
            addItemDecoration(decoration)
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
            adapter = this@MyFragment.adapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}