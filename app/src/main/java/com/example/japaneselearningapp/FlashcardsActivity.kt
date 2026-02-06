package com.example.japaneselearningapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

data class Flashcard(val text: String, val translation: String)

class FlashcardsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private val flashcards = listOf(
        Flashcard("猫", "Кот"),
        Flashcard("犬", "Собака"),
        Flashcard("学校", "Школа"),
        Flashcard("先生", "Учитель"),
        Flashcard("水", "Вода"),
        Flashcard("食べる", "Есть"),
        Flashcard("飲む", "Пить"),
        Flashcard("友達", "Друг"),
        Flashcard("日本語", "Японский язык"),
        Flashcard("ありがとう", "Спасибо")
    )

    private var index = 0
    private var showingTranslation = false

    private lateinit var cardText: TextView
    private lateinit var speakButton: Button
    private lateinit var bookmarkButton: Button
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var flipButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards)

        tts = TextToSpeech(this, this)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        cardText = findViewById(R.id.cardText)
        speakButton = findViewById(R.id.speakButton)
        bookmarkButton = findViewById(R.id.bookmarkButton)
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)
        flipButton = findViewById(R.id.flipButton)

        updateCard()

        flipButton.setOnClickListener {
            showingTranslation = !showingTranslation
            updateCard()
        }

        nextButton.setOnClickListener {
            index = (index + 1) % flashcards.size
            showingTranslation = false
            updateCard()
        }

        prevButton.setOnClickListener {
            index = if (index - 1 < 0) flashcards.size - 1 else index - 1
            showingTranslation = false
            updateCard()
        }

        speakButton.setOnClickListener {
            val text = if (showingTranslation) flashcards[index].translation else flashcards[index].text
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        bookmarkButton.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                val data = hashMapOf(
                    "type" to "flashcard",
                    "text" to flashcards[index].text,
                    "translation" to flashcards[index].translation
                )
                db.collection("users").document(user.uid)
                    .collection("bookmarks")
                    .add(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Сохранено в закладки", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Ошибка сохранения", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Вы не вошли в аккаунт", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateCard() {
        val card = flashcards[index]
        cardText.text = if (showingTranslation) card.translation else card.text
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.JAPANESE)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts.setLanguage(Locale.US)
            }
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
