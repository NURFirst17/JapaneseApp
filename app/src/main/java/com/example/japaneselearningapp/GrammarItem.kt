package com.example.japaneselearningapp

data class GrammarItem(
    val id: String,        // ID документа в Firestore
    val title: String,
    val rule: String,
    val example: String,
    val translation: String
)
