package com.hfad.dictionary.api

import com.hfad.dictionary.models.api.ExamplesResponse
import com.hfad.dictionary.models.api.DefinitionResponse
import com.hfad.dictionary.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DictionaryResponseInterface {

    @Headers(
        "${Constants.KEY_HEADER_TITLE}:${Constants.API_KEY}",
        "${Constants.HOST_HEADER_TITLE}:${Constants.API_HOST}"
    )
    @GET("words/{word}/${Constants.DEFINITION_REQUEST}")
    suspend fun getDefinition(
        @Path("word") word: String
    ): Response<DefinitionResponse>

    @Headers(
        "${Constants.KEY_HEADER_TITLE}:${Constants.API_KEY}",
        "${Constants.HOST_HEADER_TITLE}:${Constants.API_HOST}"
    )
    @GET("words/{word}/${Constants.EXAMPLE_REQUEST}")
    suspend fun getExamples(
        @Path("word") word: String
    ): Response<ExamplesResponse>
}