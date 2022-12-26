package com.hfad.dictionary.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.hfad.dictionary.models.card.Card

@Dao
interface CardDAO {
    @Query("SELECT * FROM card_table ORDER BY id ASC")
    fun getAllData():LiveData<List<Card>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(toDoData: Card)

    @Update
    suspend fun updateCard(card: Card)

    @Delete
    suspend fun deleteCard(toDoData: Card)

    @Query("DELETE FROM card_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM card_table WHERE word LIKE :query")
    fun searchThroughDatabase(query: String): LiveData<List<Card>>

    @Query("SELECT * FROM card_table ORDER BY CASE WHEN status LIKE 'N%' THEN 1 WHEN status LIKE 'R%' THEN 2 WHEN status LIKE 'L%' THEN 3 END")
    fun sortByNewStatus(): LiveData<List<Card>>

    @Query("SELECT * FROM card_table ORDER BY CASE WHEN status LIKE 'R%' THEN 1 WHEN status LIKE 'N%' THEN 2 WHEN status LIKE 'L%' THEN 3 END")
    fun sortByRepeat(): LiveData<List<Card>>

    @Query("SELECT * FROM card_table ORDER BY CASE WHEN status LIKE 'L%' THEN 1 WHEN status LIKE 'R%' THEN 2 WHEN status LIKE 'N%' THEN 3 END")
    fun sortByLowLearned(): LiveData<List<Card>>
}