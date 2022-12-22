package com.hfad.dictionary.repository

import com.hfad.dictionary.Models.DictionaryResponse
import com.hfad.dictionary.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class Repository {
    suspend fun getData(word: String):Response<DictionaryResponse> {
        return RetrofitInstance.api.getData(word)
    }
}