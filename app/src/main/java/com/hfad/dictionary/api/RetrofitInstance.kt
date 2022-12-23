package com.hfad.dictionary.api

import com.hfad.dictionary.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: DictionaryResponseInterface by lazy {
        retrofit.create(DictionaryResponseInterface::class.java)
    }
}