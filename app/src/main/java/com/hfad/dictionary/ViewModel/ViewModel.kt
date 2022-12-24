package com.hfad.dictionary.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.hfad.dictionary.models.api.ExamplesResponse
import com.hfad.dictionary.models.api.SearchResponse
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel(private val repository: Repository, application: Application):AndroidViewModel(application) {

    val myResponse: MutableLiveData<Response<SearchResponse>> = MutableLiveData()
    val myExamplesResponse: MutableLiveData<Response<ExamplesResponse>> = MutableLiveData()

    val cardDao = CardDatabase.getDatabase(
        application
    ).cardDao()
    private val MyRepository : Repository
    val getAllData: LiveData<List<Card>>
    val sortByLearned: LiveData<List<Card>>
    val sortByNew: LiveData<List<Card>>
    val sortByRepeat: LiveData<List<Card>>

    init {
        MyRepository = Repository(cardDao)
        getAllData = MyRepository.getAllData
        sortByLearned = MyRepository.sortByLearned
        sortByNew = MyRepository.sortByNew
        sortByRepeat = MyRepository.sortByRepeat
    }

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

    //room
    fun insertCard(card: Card){
        viewModelScope.launch(Dispatchers.IO) {
            MyRepository.insertCard(card)
        }
    }

    fun updateCard(card: Card){
        viewModelScope.launch(Dispatchers.IO){
            MyRepository.updateCard(card)
        }
    }

    fun deleteCard(card: Card){
        viewModelScope.launch(Dispatchers.IO){
            MyRepository.deleteCard(card)
        }
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            MyRepository.deleteAll()
        }
    }

    fun searchThroughDatabase(query: String):LiveData<List<Card>>{
        return MyRepository.searchThroughDatabase(query)
    }
}