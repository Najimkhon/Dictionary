package com.hfad.dictionary.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.hfad.dictionary.ViewModel.CardDAO
import com.hfad.dictionary.models.card.Card


class RoomRepository(private val cardDao: CardDAO) {
    val getAllData:LiveData<List<Card>> = cardDao.getAllData()
    val sortByLearned:LiveData<List<Card>> = cardDao.sortByLowLearned()
    val sortByNew:LiveData<List<Card>> = cardDao.sortByNewStatus()
    val sortByRepeat:LiveData<List<Card>> = cardDao.sortByRepeat()

    suspend fun insertCard(toDoData: Card){
        cardDao.insertCard(toDoData)
    }
    suspend fun updateCard(card: Card){
        cardDao.updateCard(card)
        Log.d("Test", "rep works")
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
}