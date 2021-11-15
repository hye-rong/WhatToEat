package com.example.uxdesign.ui.search


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uxdesign.model.data.Place
import com.example.uxdesign.model.data.PlaceLoc
import com.example.uxdesign.repository.KakaoAPIRepositoryImpl
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class PlaceViewModel:ViewModel() {
    var placeList =  MutableLiveData<List<PlaceLoc>>()
    var loc = MutableLiveData<LatLng>()
    fun getPlaceList(loc: LatLng){
        viewModelScope.launch {
            placeList.value = KakaoAPIRepositoryImpl.searchByLatLng(loc)
            Log.d("testKAKAOAPI", placeList.value.toString())
        }
    }
    fun getLocation(key: String){
        viewModelScope.launch {
            val locationInfo = KakaoAPIRepositoryImpl.searchLocation(key)[0]
            loc.value = LatLng(locationInfo.y.toDouble(), locationInfo.x.toDouble())
            Log.d("testKAKAOAPI", loc.value.toString())
        }
    }

}