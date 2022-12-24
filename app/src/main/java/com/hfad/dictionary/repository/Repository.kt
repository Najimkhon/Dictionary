package com.hfad.dictionary.repository

import androidx.lifecycle.LiveData
import com.hfad.dictionary.ViewModel.CardDAO
import com.hfad.dictionary.api.RetrofitInstance
import com.hfad.dictionary.models.api.ExamplesResponse
import com.hfad.dictionary.models.api.SearchResponse
import com.hfad.dictionary.models.card.Card
import retrofit2.Response

class Repository(private val cardDao: CardDAO) {
    val getAllData: LiveData<List<Card>> = cardDao.getAllData()
    val sortByLearned: LiveData<List<Card>> = cardDao.sortByLowLearned()
    val sortByNew: LiveData<List<Card>> = cardDao.sortByNewStatus()
    val sortByRepeat: LiveData<List<Card>> = cardDao.sortByRepeat()

    //Api
    suspend fun getDefinition(word: String):Response<SearchResponse> {
        return RetrofitInstance.api.getDefinition(word)
    }

    suspend fun getExamples(word: String):Response<ExamplesResponse> {
        return RetrofitInstance.api.getExamples(word)
    }

    //Room
    suspend fun insertData(card: Card){
        cardDao.insertData(card)
    }
    suspend fun updateItem(card: Card){
        cardDao.updateItem(card)
    }

    suspend fun deleteItem(card: Card){
        cardDao.deleteItem(card)
    }

    suspend fun deleteAll(){
        cardDao.deleteAll()
    }

    fun searchThroughDatabase(query: String):LiveData<List<Card>>{
        return cardDao.searchThroughDatabase(query)
    }
}