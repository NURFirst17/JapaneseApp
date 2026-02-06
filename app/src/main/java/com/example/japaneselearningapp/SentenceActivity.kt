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

data class Sentence(val text: String, val translation: String)

class SentenceActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private val sentences = listOf(
        Sentence("私は学生です。", "Я студент."),
        Sentence("これはペンです。", "Это ручка."),
        Sentence("天気がいいですね。", "Хорошая погода, не правда ли?"),
        Sentence("今日は忙しいです。", "Сегодня я занят."),
        Sentence("猫が好きです。", "Мне нравятся кошки."),
        Sentence("これは何ですか？", "Что это?"),
        Sentence("図書館に行きます。", "Я иду в библиотеку."),
        Sentence("コーヒーを飲みます。", "Я пью кофе."),
        Sentence("日本語を勉強しています。", "Я учу японский."),
        Sentence("明日会いましょう。", "Давай встретимся завтра.")
    )

    private var index = 0

    private lateinit var sentenceText: TextView
    private lateinit var translationText: TextView
    private lateinit var speakButton: Button
    private lateinit var bookmarkButton: Button
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_list)

        tts = TextToSpeech(this, this)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        sentenceText = findViewById(R.id.sentenceText)
        translationText = findViewById(R.id.translationText)
        speakButton = findViewById(R.id.speakButton)
        bookmarkButton = findViewById(R.id.bookmarkButton)
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)

        updateSentence()

        speakButton.setOnClickListener {
            tts.speak(sentences[index].text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        bookmarkButton.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                val data = hashMapOf(
                    "type" to "sentence",
                    "text" to sentences[index].text,
                    "translation" to sentences[index].translation
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

        nextButton.setOnClickListener {
            index = (index + 1) % sentences.size
            updateSentence()
        }

        prevButton.setOnClickListener {
            index = if (index - 1 < 0) sentences.size - 1 else index - 1
            updateSentence()
        }
    }

    private fun updateSentence() {
        val s = sentences[index]
        sentenceText.text = s.text
        translationText.text = s.translation
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
