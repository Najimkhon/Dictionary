package com.hfad.dictionary.repository

import com.hfad.dictionary.api.RetrofitInstance
import com.hfad.dictionary.models.api.SearchResponse
import retrofit2.Response

class Repository {
    suspend fun getDefinition(word: String):Response<SearchResponse> {
        return RetrofitInstance.api.getDefinition(word)
    }
}