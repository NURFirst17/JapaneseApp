package com.example.japaneselearningapp

data class Bookmark(
    val id: String,
    val type: String,        // "word", "flashcard", "sentence"
    val text: String,        // основной текст закладки
    val translation: String = "" // перевод или пояснение
)
