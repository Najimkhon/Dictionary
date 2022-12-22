package com.hfad.dictionary.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.dictionary.Models.DictionaryResponse
import com.hfad.dictionary.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ViewModel(private val repository: Repository):ViewModel() {

    val myResponse: MutableLiveData<Response<DictionaryResponse>> = MutableLiveData()

    fun getData(word:String){
        viewModelScope.launch {
            val response = repository.getData(word)
            myResponse.value = response
        }

    }
}