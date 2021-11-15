package com.example.uxdesign.ui.theme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uxdesign.model.data.Place
import com.example.uxdesign.repository.KakaoAPIRepositoryImpl
import kotlinx.coroutines.launch

class ThemeViewModel:ViewModel() {
    var searchList = MutableLiveData<List<Place>>()
    fun getSearchList(key:String){
        viewModelScope.launch {
            searchList.value = KakaoAPIRepositoryImpl.searchKeyword(key)
        }
    }

}