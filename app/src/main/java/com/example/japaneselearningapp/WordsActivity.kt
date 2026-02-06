package com.example.japaneselearningapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

data class Word(val japanese: String, val translation: String)

class WordsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech

    private val words = listOf(
        Word("猫", "Кот"),
        Word("犬", "Собака"),
        Word("本", "Книга"),
        Word("水", "Вода"),
        Word("火", "Огонь"),
        Word("空", "Небо"),
        Word("山", "Гора"),
        Word("川", "Река"),
        Word("花", "Цветок"),
        Word("車", "Машина"),
        Word("道", "Дорога"),
        Word("家", "Дом"),
        Word("学校", "Школа"),
        Word("友達", "Друг"),
        Word("先生", "Учитель"),
        Word("時", "Время"),
        Word("朝", "Утро"),
        Word("夜", "Ночь"),
        Word("月", "Луна"),
        Word("太陽", "Солнце")
    )

    private var index = 0

    private lateinit var wordText: TextView
    private lateinit var translationText: TextView
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var speakButton: Button
    private lateinit var bookmarkButton: Button

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        tts = TextToSpeech(this, this)

        wordText = findViewById(R.id.wordText)
        translationText = findViewById(R.id.translationText)
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)
        speakButton = findViewById(R.id.speakButton)
        bookmarkButton = findViewById(R.id.bookmarkButton)

        updateWord()

        nextButton.setOnClickListener {
            index = (index + 1) % words.size
            updateWord()
        }

        prevButton.setOnClickListener {
            index = if (index - 1 < 0) words.size - 1 else index - 1
            updateWord()
        }

        speakButton.setOnClickListener {
            val word = words[index]
            tts.speak(word.japanese, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        bookmarkButton.setOnClickListener {
            val word = words[index]
            user?.uid?.let { uid ->
                val docRef = db.collection("users").document(uid)
                    .collection("bookmarks")
                    .document(word.japanese)

                docRef.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        // Если есть, удаляем
                        docRef.delete()
                        updateBookmarkButton(false)
                    } else {
                        // Если нет, добавляем
                        docRef.set(word)
                        updateBookmarkButton(true)
                    }
                }
            }
        }
    }

    private fun updateWord() {
        val word = words[index]
        wordText.text = word.japanese
        translationText.text = word.translation

        checkBookmarkStatus()
    }

    private fun checkBookmarkStatus() {
        user?.uid?.let { uid ->
            val word = words[index]
            val docRef = db.collection("users").document(uid)
                .collection("bookmarks")
                .document(word.japanese)

            docRef.get().addOnSuccessListener { snapshot ->
                updateBookmarkButton(snapshot.exists())
            }
        }
    }

    private fun updateBookmarkButton(isBookmarked: Boolean) {
        if (isBookmarked) {
            bookmarkButton.text = "Удалить из закладок"
        } else {
            bookmarkButton.text = "В закладки"
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.JAPANESE
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
