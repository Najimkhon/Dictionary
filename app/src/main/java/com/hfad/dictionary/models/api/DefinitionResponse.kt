package com.hfad.dictionary.models.api

data class DefinitionResponse(
    val definitions: List<Definition>,
    val word: String
)