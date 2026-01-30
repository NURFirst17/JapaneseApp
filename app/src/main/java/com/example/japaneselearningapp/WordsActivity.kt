package com.example.japaneselearningapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        val wordText = findViewById<TextView>(R.id.wordText)
        val nextButton = findViewById<Button>(R.id.nextWordButton)

        wordText.text = words[index]

        nextButton.setOnClickListener {
            index = (index + 1) % words.size
            wordText.text = words[index]
        }
    }
}
