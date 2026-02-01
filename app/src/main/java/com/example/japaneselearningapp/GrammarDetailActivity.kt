package com.example.japaneselearningapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GrammarDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar_detail)

        val titleTv: TextView = findViewById(R.id.titleTv)
        val ruleTv: TextView = findViewById(R.id.ruleTv)
        val exampleTv: TextView = findViewById(R.id.exampleTv)
        val translationTv: TextView = findViewById(R.id.translationTv)

        titleTv.text = intent.getStringExtra("title")
        ruleTv.text = intent.getStringExtra("rule")
        exampleTv.text = intent.getStringExtra("example")
        translationTv.text = intent.getStringExtra("translation")
    }
}
