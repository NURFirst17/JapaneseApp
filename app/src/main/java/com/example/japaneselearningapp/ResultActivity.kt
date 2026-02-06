package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        val scoreText = findViewById<TextView>(R.id.scoreText)
        val percentText = findViewById<TextView>(R.id.percentText)
        val retryButton = findViewById<Button>(R.id.retryButton)
        val menuButton = findViewById<Button>(R.id.menuButton)

        scoreText.text = "Правильные ответы: $score/$total"
        val percent = if (total > 0) (score * 100 / total) else 0
        percentText.text = "Процент: $percent%"

        retryButton.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
            finish()
        }

        menuButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}
