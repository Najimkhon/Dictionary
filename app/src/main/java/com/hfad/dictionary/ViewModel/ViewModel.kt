package com.hfad.dictionary.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.hfad.dictionary.models.api.ExamplesResponse
import com.hfad.dictionary.models.api.SearchResponse
import com.hfad.dictionary.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel(private val repository: Repository, application: Application):AndroidViewModel(application) {

    val myResponse: MutableLiveData<Response<SearchResponse>> = MutableLiveData()
    val myExamplesResponse: MutableLiveData<Response<ExamplesResponse>> = MutableLiveData()

    fun getData(word:String){
        viewModelScope.launch {
            val response = repository.getDefinition(word)
            myResponse.value = response
            Log.d("resp", response.isSuccessful.toString())
        }
    }

    fun getExamples(word:String){
        viewModelScope.launch {
            val response = repository.getExamples(word)
            myExamplesResponse.value = response
            Log.d("respEx", response.isSuccessful.toString())
        }
    }


}