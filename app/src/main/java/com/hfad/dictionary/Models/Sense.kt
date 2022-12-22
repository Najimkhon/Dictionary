package com.hfad.dictionary.Models

data class Sense(
    val definitions: List<String>,
    val domainClasses: List<DomainClasse>,
    val examples: List<Example>,
    val id: String,
    val regions: List<Region>,
    val registers: List<RegisterX>,
    val semanticClasses: List<SemanticClasse>,
    val shortDefinitions: List<String>,
    val subsenses: List<Subsense>,
    val synonyms: List<Synonym>,
    val thesaurusLinks: List<ThesaurusLink>
)