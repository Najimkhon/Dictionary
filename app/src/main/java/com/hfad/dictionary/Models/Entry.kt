package com.hfad.dictionary.Models

data class Entry(
    val etymologies: List<String>,
    val grammaticalFeatures: List<GrammaticalFeature>,
    val homographNumber: String,
    val pronunciations: List<Pronunciation>,
    val senses: List<Sense>
)