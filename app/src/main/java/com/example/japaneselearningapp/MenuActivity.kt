package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.alphabetButton).setOnClickListener {
            startActivity(Intent(this, AlphabetActivity::class.java))
        }

        findViewById<Button>(R.id.wordsButton).setOnClickListener {
            startActivity(Intent(this, WordsActivity::class.java))
        }
    }
}
