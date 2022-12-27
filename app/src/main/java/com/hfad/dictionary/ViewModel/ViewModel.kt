package com.hfad.dictionary.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.hfad.dictionary.models.api.ExamplesResponse
import com.hfad.dictionary.models.api.SearchResponse
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.repository.Repository
import com.hfad.dictionary.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    private val cardDao = CardDatabase.getDatabase(
        application
    ).cardDao()
    private val roomRepository: RoomRepository
    val getAllData: LiveData<List<Card>>
    val sortByNew: LiveData<List<Card>>
    val sortByRepeat: LiveData<List<Card>>
    val sortByLearned: LiveData<List<Card>>

    init {
        roomRepository = RoomRepository(cardDao)
        getAllData = roomRepository.getAllData
        sortByNew = roomRepository.sortByNew
        sortByLearned = roomRepository.sortByLearned
        sortByRepeat = roomRepository.sortByRepeat
    }
    //api
    val myResponse: MutableLiveData<Response<SearchResponse>> = MutableLiveData()
    val myExamplesResponse: MutableLiveData<Response<ExamplesResponse>> = MutableLiveData()

    fun getData(word: String) {
        viewModelScope.launch {
            val response = repository.getDefinition(word)
            myResponse.value = response
            Log.d("resp", response.isSuccessful.toString())
        }
    }

    fun getExamples(word: String) {
        viewModelScope.launch {
            val response = repository.getExamples(word)
            myExamplesResponse.value = response
            Log.d("respEx", response.isSuccessful.toString())
        }
    }

    //room
    fun insertData(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertCard(card)
        }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.updateCard(card)
            Log.d("Test", "viewmodel works")
        }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deleteCard(card)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deleteAll()
        }
    }

    fun searchThroughDatabase(query: String): LiveData<List<Card>> {
        return roomRepository.searchThroughDatabase(query)
    }

}