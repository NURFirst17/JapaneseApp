package com.example.japaneselearningapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FlashcardDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard_detail)

        val questionTv = findViewById<TextView>(R.id.questionTv)
        val answerTv = findViewById<TextView>(R.id.answerTv)

        questionTv.text = intent.getStringExtra("question")
        answerTv.text = intent.getStringExtra("answer")
    }
}
