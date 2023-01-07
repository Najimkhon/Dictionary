package com.hfad.dictionary.repository

import androidx.lifecycle.LiveData
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.viewmodel.CardDAO

class RoomRepository(private val cardDao: CardDAO) {

    val getAllData: LiveData<List<Card>> = cardDao.getAllData()
    val sortByLearned: LiveData<List<Card>> = cardDao.sortByLowLearned()
    val sortByNew: LiveData<List<Card>> = cardDao.sortByNewStatus()
    val sortByRepeat: LiveData<List<Card>> = cardDao.sortByRepeat()

    suspend fun insertCard(toDoData: Card) {
        cardDao.insertCard(toDoData)
    }

    suspend fun updateCard(card: Card) {
        cardDao.updateCard(card)
    }

    suspend fun deleteCard(card: Card) {
        cardDao.deleteCard(card)
    }

    suspend fun deleteAll() {
        cardDao.deleteAll()
    }

    fun searchThroughDatabase(query: String): LiveData<List<Card>> {
        return cardDao.searchThroughDatabase(query)
    }
}