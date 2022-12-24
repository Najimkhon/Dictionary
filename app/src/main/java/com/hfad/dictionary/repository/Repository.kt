package com.hfad.dictionary.repository

import com.hfad.dictionary.api.RetrofitInstance
import com.hfad.dictionary.models.api.ExamplesResponse
import com.hfad.dictionary.models.api.SearchResponse
import retrofit2.Response

class Repository {
    suspend fun getDefinition(word: String):Response<SearchResponse> {
        return RetrofitInstance.api.getDefinition(word)
    }

    suspend fun getExamples(word: String):Response<ExamplesResponse> {
        return RetrofitInstance.api.getExamples(word)
    }
<<<<<<< HEAD

    //Room
    suspend fun insertCard(card: Card){
        cardDao.insertCard(card)
    }
    suspend fun updateCard(card: Card){
        cardDao.updateCard(card)
    }

    suspend fun deleteCard(card: Card){
        cardDao.deleteCard(card)
    }

    suspend fun deleteAll(){
        cardDao.deleteAll()
    }

    fun searchThroughDatabase(query: String):LiveData<List<Card>>{
        return cardDao.searchThroughDatabase(query)
    }
=======
>>>>>>> parent of 0f4ac88 (add room to repository)
}