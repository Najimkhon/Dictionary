package com.hfad.dictionary.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hfad.dictionary.models.api.DefinitionResponse
import com.hfad.dictionary.models.api.ExamplesResponse
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.models.card.Status
import com.hfad.dictionary.repository.NetworkRepository
import com.hfad.dictionary.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) :
    AndroidViewModel(application) {

    // room
    private val cardDao = CardDatabase.getDatabase(application).cardDao()
    private val roomRepository: RoomRepository = RoomRepository(cardDao)
    val getAllData: LiveData<List<Card>> = roomRepository.getAllData
    val sortByNew: LiveData<List<Card>> = roomRepository.sortByNew
    val sortByRepeat: LiveData<List<Card>> = roomRepository.sortByRepeat
    val sortByLearned: LiveData<List<Card>> = roomRepository.sortByLearned

    //api
    private val networkRepository: NetworkRepository = NetworkRepository()
    val wordResultListLiveData: MutableLiveData<List<Card>> = MutableLiveData()

    init {
        println("hop: ViewModel is created")
    }

    // todo: ui state with viewmodel communication for network error, empty list...
    fun searchForWord(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val definitionsResponse = networkRepository.fetchDefinitions(word)
            val examplesResponse = networkRepository.fetchExamples(word) // low priority

            if (definitionsResponse == null || examplesResponse == null) {
                // we had an issue with network
            } else {
                val cardList = generateWordCardList(word, definitionsResponse, examplesResponse)
                wordResultListLiveData.postValue(cardList)
            }
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

    private fun generateWordCardList(word: String, definitionsResponse: DefinitionResponse, examplesResponse: ExamplesResponse): List<Card> {
        val definitionsList = definitionsResponse.definitions
        val examplesList = examplesResponse.examples

        val cardList = arrayListOf<Card>()
        for (i in definitionsList.indices) {
            if (i < examplesList.size) {
                cardList.add(
                    Card(
                        0,
                        word,
                        Status.New,
                        definitionsList[i].partOfSpeech,
                        definitionsList[i].definition,
                        examplesList[i]
                    )
                )
            } else {
                cardList.add(
                    Card(
                        0,
                        word,
                        Status.New,
                        definitionsList[i].partOfSpeech,
                        definitionsList[i].definition,
                        "No example available. Please, write your own!"
                    )
                )
            }
        }
    }

}