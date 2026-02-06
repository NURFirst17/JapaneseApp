package com.example.japaneselearningapp

// Data классы

object AppData {
    data class Word(val text: String, val translation: String)
    data class Flashcard(val text: String, val translation: String)
    data class Sentence(val text: String, val translation: String)
    // Слова (text - японское слово, translation - перевод)
    val words = listOf(
        Word("猫", "Кот"),
        Word("犬", "Собака"),
        Word("本", "Книга"),
        Word("水", "Вода"),
        Word("火", "Огонь"),
        Word("木", "Дерево"),
        Word("空", "Небо"),
        Word("山", "Гора"),
        Word("川", "Река"),
        Word("雨", "Дождь"),
        Word("風", "Ветер"),
        Word("花", "Цветок"),
        Word("月", "Луна"),
        Word("太陽", "Солнце"),
        Word("星", "Звезда"),
        Word("海", "Море"),
        Word("森", "Лес"),
        Word("道", "Дорога"),
        Word("町", "Город"),
        Word("家", "Дом")
    )

    // Карточки (text - японский символ или слово, translation - пояснение)
    val flashcards = listOf(
        Flashcard("ありがとう", "Спасибо"),
        Flashcard("こんにちは", "Здравствуйте"),
        Flashcard("さようなら", "До свидания"),
        Flashcard("すみません", "Извините"),
        Flashcard("おはよう", "Доброе утро"),
        Flashcard("こんばんは", "Добрый вечер"),
        Flashcard("はい", "Да"),
        Flashcard("いいえ", "Нет"),
        Flashcard("お願いします", "Пожалуйста (при просьбе)"),
        Flashcard("どういたしまして", "Не за что")
    )

    // Предложения (text - японское предложение, translation - перевод)
    val sentences = listOf(
        Sentence("私は学生です", "Я студент"),
        Sentence("これは本です", "Это книга"),
        Sentence("犬がいます", "Есть собака"),
        Sentence("今日は雨です", "Сегодня дождь"),
        Sentence("明日は山に行きます", "Завтра пойду в горы"),
        Sentence("私は日本語を勉強しています", "Я изучаю японский язык"),
        Sentence("これは美味しいです", "Это вкусно"),
        Sentence("電車に乗ります", "Я еду на поезде"),
        Sentence("公園で遊びます", "Играю в парке"),
        Sentence("友達と映画を見ます", "Смотрю фильм с друзьями")
    )
}

