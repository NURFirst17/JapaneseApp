package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var startButton: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcomeTextView = findViewById(R.id.titleText)
        startButton = findViewById(R.id.startButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Получаем имя пользователя из Firestore
            db.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    val name = document.getString("name") ?: "Гость"
                    welcomeTextView.text = "Здравствуйте, $name"
                }
                .addOnFailureListener { e ->
                    welcomeTextView.text = "Здравствуйте, Гость"
                    Toast.makeText(this, "Ошибка загрузки имени: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            welcomeTextView.text = "Здравствуйте, Гость"
        }
        startActivity(Intent(this, GrammarActivity::class.java))
        // Переход в MenuActivity
        startButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()  // Выходим из Firebase
            Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
            // Возврат на LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}