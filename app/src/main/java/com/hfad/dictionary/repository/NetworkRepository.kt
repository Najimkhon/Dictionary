package com.hfad.dictionary.repository

import android.util.Log
import com.hfad.dictionary.api.RetrofitInstance
import com.hfad.dictionary.models.api.DefinitionResponse
import com.hfad.dictionary.models.api.ExamplesResponse

class NetworkRepository {

    suspend fun fetchDefinitions(word: String): DefinitionResponse? {
        val response = RetrofitInstance.api.getDefinition(word)
        Log.d("resp", response.isSuccessful.toString())
        return if (response.isSuccessful) {
            return response.body()
        } else {
            null
        }
    }

    suspend fun fetchExamples(word: String): ExamplesResponse? {
        val response = RetrofitInstance.api.getExamples(word)
        Log.d("respEx", response.isSuccessful.toString())
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

}