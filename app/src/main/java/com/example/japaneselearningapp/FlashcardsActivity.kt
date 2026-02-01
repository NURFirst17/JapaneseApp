package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class FlashcardsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private val flashcardsList = mutableListOf<FlashcardItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards)

        recyclerView = findViewById(R.id.flashcardsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = FlashcardAdapter(flashcardsList) { item ->
            val intent = Intent(this, FlashcardDetailActivity::class.java)
            intent.putExtra("question", item.question)
            intent.putExtra("answer", item.answer)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        loadFlashcards(adapter)
    }

    private fun loadFlashcards(adapter: FlashcardAdapter) {
        db.collection("flashcards")
            .get()
            .addOnSuccessListener { result ->
                flashcardsList.clear()
                for (doc in result.documents) {
                    val item = FlashcardItem(
                        id = doc.id,
                        question = doc.getString("question") ?: "",
                        answer = doc.getString("answer") ?: ""
                    )
                    flashcardsList.add(item)
                }
                adapter.notifyDataSetChanged()
            }
    }
}
