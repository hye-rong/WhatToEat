package com.example.uxdesign.ui.theme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uxdesign.R
import com.example.uxdesign.databinding.FragmentThemeBinding
import com.example.uxdesign.ui.search.SearchFragment

class ThemeFragment : Fragment() {
    lateinit var binding:FragmentThemeBinding
    private val themeViewModel: ThemeViewModel by activityViewModels()
    lateinit var adapter:ThemeRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThemeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtn()
        initRecyclerView()
        subscribeObservers()

    }
    private fun subscribeObservers() {
        themeViewModel.searchList.observe(viewLifecycleOwner){
            //recyclerView갱신
            if(it==null){
                binding.showMain.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.INVISIBLE
                binding.showText.visibility = View.VISIBLE
                binding.showArrow.visibility = View.GONE
                adapter.submitList(it)
            }
            else{
                binding.showMain.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                binding.showText.visibility = View.GONE
                binding.showArrow.visibility = View.VISIBLE
                adapter.submitList(it)
            }
        }

    }

    private fun initBtn(){
        binding.apply {
            btn1.setOnClickListener { btnClick(btn1.text.toString())}
            btn2.setOnClickListener { btnClick(btn2.text.toString()) }
            btn3.setOnClickListener { btnClick(btn3.text.toString()) }
            btn4.setOnClickListener { btnClick(btn4.text.toString()) }
            btn5.setOnClickListener { btnClick(btn5.text.toString()) }
            btn6.setOnClickListener { btnClick(btn6.text.toString()) }
            btn7.setOnClickListener { btnClick(btn7.text.toString()) }
            btn8.setOnClickListener { btnClick(btn8.text.toString()) }


            cbtn1.setOnClickListener {  }
            cbtn2.setOnClickListener {  }
            cbtn3.setOnClickListener {  }
            cbtn4.setOnClickListener {  }
        }
        binding.showArrow.setOnClickListener {
            themeViewModel.searchList.value = null
            binding.editText.text = null
        }
    }
    private fun btnClick(inputText:String){
        themeViewModel.getSearchList(inputText)
        binding.editText.setText(inputText)
    }
    private fun initRecyclerView(){
        adapter = ThemeRecyclerViewAdapter()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

}