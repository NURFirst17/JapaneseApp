package com.example.japaneselearningapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WordsActivity : AppCompatActivity() {

    private val words = listOf(
        "猫 – кот (neko)",
        "犬 – собака (inu)",
        "水 – вода (mizu)",
        "火 – огонь (hi)",
        "山 – гора (yama)"
    )
    private var index = 0
    private lateinit var bookmarkManager: BookmarkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        val wordText = findViewById<TextView>(R.id.wordText)
        val nextButton = findViewById<Button>(R.id.nextWordButton)
        val bookmarkButton = findViewById<Button>(R.id.bookmarkButton)

        wordText.text = words[index]
        bookmarkManager = BookmarkManager()

        nextButton.setOnClickListener {
            index = (index + 1) % words.size
            wordText.text = words[index]
        }

        bookmarkButton.setOnClickListener {
            val currentWord = words[index]
            bookmarkManager.addBookmark(currentWord,
                onSuccess = {
                    Toast.makeText(this, "Слово добавлено в закладки!", Toast.LENGTH_SHORT).show()
                },
                onFailure = { e ->
                    Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}