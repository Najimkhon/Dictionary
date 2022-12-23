package com.hfad.dictionary.api

import com.hfad.dictionary.Models.DictionaryResponse
import com.hfad.dictionary.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DictionaryResponseInterface {
    @Headers(
        "Accept:application/json",
        "app_id:${Constants.APP_ID}",
        "app_key:${Constants.APP_KEY}"
    )
    @GET("en-gb/{word}")
    suspend fun getData(
        @Path("word") word: String
    ): Response<DictionaryResponse>
}