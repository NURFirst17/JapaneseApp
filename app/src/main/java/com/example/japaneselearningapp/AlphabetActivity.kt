package com.example.japaneselearningapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlphabetActivity : AppCompatActivity() {

    private val hiragana = listOf(
        "あ – a",
        "い – i",
        "う – u",
        "え – e",
        "お – o"
    )

    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alphabet)

        Toast.makeText(this, "Изучаем хирагану", Toast.LENGTH_SHORT).show()

        val symbolText = findViewById<TextView>(R.id.symbolText)
        val nextButton = findViewById<Button>(R.id.nextButton)

        symbolText.text = hiragana[index]

        nextButton.setOnClickListener {
            index = (index + 1) % hiragana.size
            symbolText.text = hiragana[index]
        }
    }
}
