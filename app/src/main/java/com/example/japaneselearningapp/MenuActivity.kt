package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val welcomeText = findViewById<TextView>(R.id.welcomeText)

        val user = auth.currentUser

        if (user != null) {

            db.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->

                    val name = document.getString("name")

                    welcomeText.text =
                        if (!name.isNullOrEmpty())
                            "Здравствуйте, $name!"
                        else
                            "Здравствуйте!"
                }
                .addOnFailureListener {
                    welcomeText.text = "Здравствуйте!"
                }
        }

        // Кнопки
        findViewById<Button>(R.id.alphabetButton).setOnClickListener {
            startActivity(Intent(this, AlphabetActivity::class.java))
        }

        findViewById<Button>(R.id.wordsButton).setOnClickListener {
            startActivity(Intent(this, WordsActivity::class.java))
        }

        findViewById<Button>(R.id.grammarButton).setOnClickListener {
            startActivity(Intent(this, GrammarActivity::class.java))
        }

        findViewById<Button>(R.id.flashcardsButton).setOnClickListener {
            startActivity(Intent(this, FlashcardsActivity::class.java))
        }

        findViewById<Button>(R.id.sentencesButton).setOnClickListener {
            startActivity(Intent(this, SentenceActivity::class.java))
        }

        findViewById<Button>(R.id.bookmarksButton).setOnClickListener {
            startActivity(Intent(this, BookmarksActivity::class.java))
        }
        findViewById<Button>(R.id.testButton).setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }
}
