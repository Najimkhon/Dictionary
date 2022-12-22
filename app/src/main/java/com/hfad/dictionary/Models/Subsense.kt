package com.hfad.dictionary.Models

data class Subsense(
    val definitions: List<String>,
    //val domainClasses: List<DomainClasseX>,
    val domains: List<Domain>,
    val examples: List<ExampleX>,
    val id: String,
    //val notes: List<NoteX>,
    val registers: List<RegisterX>,
    //val semanticClasses: List<SemanticClasseX>,
    val shortDefinitions: List<String>
)