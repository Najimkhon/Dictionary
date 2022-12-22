package com.hfad.dictionary.Models

data class DictionaryResponse(
    val id: String,
    val metadata: Metadata,
    val results: List<Result>,
    val word: String
)