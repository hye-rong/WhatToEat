package com.example.uxdesign.repository

import com.example.uxdesign.R
import com.example.uxdesign.application.GlobalApplication
import com.example.uxdesign.model.KakaoAPIService
import com.example.uxdesign.model.data.*
import com.google.android.gms.maps.model.LatLng
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

object KakaoAPIRepositoryImpl : KakaoAPIRepository {
    val BASE_URL = "https://dapi.kakao.com/"
    val Category_Group_Code = "FD6" // 음식점

    override suspend fun searchKeyword(keyword: String): List<Place>  {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPIService::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(
            GlobalApplication.getContext().getString(R.string.API_KEY),
                keyword,
                Category_Group_Code
        )

        // API 서버에 요청
        val resultSearchKeyword: ResultSearchKeyword = call.await()
        return resultSearchKeyword.documents
    }
    override suspend fun searchByLatLng(latLng: LatLng): List<PlaceLoc>  {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPIService::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getSearchLatLng(
            GlobalApplication.getContext().getString(R.string.API_KEY),
            Category_Group_Code,
            latLng.longitude.toString(),
            latLng.latitude.toString()
        )

        // API 서버에 요청
        val resultSearchLatLng: ResultSearchLatLng = call.await()
        return resultSearchLatLng.documents
    }

    override suspend fun searchLocation(key: String): List<LocData>  {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPIService::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getLocation(
            GlobalApplication.getContext().getString(R.string.API_KEY),
            key
        )

        // API 서버에 요청
        val resultLocation: ResultLocation = call.await()
        return resultLocation.documents
    }
}