package com.example.uxdesign.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uxdesign.ui.favorite.MyFragment
import com.example.uxdesign.R
import com.example.uxdesign.ui.search.SearchFragment
import com.example.uxdesign.databinding.ActivityMainBinding
import com.example.uxdesign.model.KakaoAPIService
import com.example.uxdesign.model.data.ResultSearchKeyword
import com.example.uxdesign.ui.theme.ThemeFragment
import com.example.uxdesign.ui.search.PlaceViewModel
import com.google.android.gms.maps.GoogleMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    //val placeViewModel: PlaceViewModel by viewModels()

    val myFragment = MyFragment()
    val searchFragment = SearchFragment()
    val themeFragment = ThemeFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavigation()

    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun initNavigation(){
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_my -> {
                    replaceFragment(myFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.action_search -> {
                    replaceFragment(searchFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    replaceFragment(themeFragment)
                    return@setOnItemSelectedListener true
                }
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.action_my
    }
}