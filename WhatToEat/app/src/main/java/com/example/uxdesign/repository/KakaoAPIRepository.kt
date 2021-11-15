package com.example.uxdesign.repository

import android.util.Log
import com.example.uxdesign.model.KakaoAPIService
import com.example.uxdesign.model.data.LocData
import com.example.uxdesign.model.data.Place
import com.example.uxdesign.model.data.PlaceLoc
import com.example.uxdesign.model.data.ResultSearchKeyword
import com.example.uxdesign.ui.MainActivity
import com.google.android.gms.maps.model.LatLng
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

interface KakaoAPIRepository {
    suspend fun searchKeyword(keyword: String) : List<Place>
    suspend fun searchByLatLng(latLng: LatLng): List<PlaceLoc>
    suspend fun searchLocation(key: String): List<LocData>
}