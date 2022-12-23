package com.hfad.dictionary.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.dictionary.models.api.SearchResponse
import com.hfad.dictionary.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel(private val repository: Repository):ViewModel() {

    val myResponse: MutableLiveData<Response<SearchResponse>> = MutableLiveData()

    fun getData(word:String){
        viewModelScope.launch {
            val response = repository.getDefinition(word)
            myResponse.value = response
            Log.d("resp", response.isSuccessful.toString())
        }

    }
}