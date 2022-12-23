package com.hfad.dictionary.models.api

data class SearchResponse(
    val definitions: List<Definition>,
    val word: String
)