package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class BookmarksActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookmarksAdapter
    private var listener: ListenerRegistration? = null
    private val allBookmarks = mutableListOf<Bookmark>()
    private val filteredBookmarks = mutableListOf<Bookmark>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.bookmarksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BookmarksAdapter(filteredBookmarks,
            onDeleteClick = { bookmark -> deleteBookmark(bookmark) },
            onItemClick = { bookmark -> openBookmark(bookmark) }
        )
        recyclerView.adapter = adapter

        setupFilterButtons()
        loadBookmarks()
    }

    private fun setupFilterButtons() {
        val allBtn = findViewById<Button>(R.id.filterAll)
        val wordsBtn = findViewById<Button>(R.id.filterWords)
        val flashcardsBtn = findViewById<Button>(R.id.filterFlashcards)
        val sentencesBtn = findViewById<Button>(R.id.filterGrammar) // кнопка "Предложения"

        allBtn.setOnClickListener { filterBookmarks("all") }
        wordsBtn.setOnClickListener { filterBookmarks("word") }
        flashcardsBtn.setOnClickListener { filterBookmarks("flashcard") }
        sentencesBtn.setOnClickListener { filterBookmarks("sentence") } // заменили "grammar" на "sentence"
    }


    private fun filterBookmarks(type: String) {
        filteredBookmarks.clear()
        if (type == "all") {
            filteredBookmarks.addAll(allBookmarks)
        } else {
            filteredBookmarks.addAll(allBookmarks.filter { it.type == type })
        }
        adapter.notifyDataSetChanged()
    }

    private fun loadBookmarks() {
        val user = auth.currentUser
        if (user != null) {
            listener = db.collection("users").document(user.uid)
                .collection("bookmarks")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Toast.makeText(this, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        allBookmarks.clear()
                        for (doc in snapshot.documents) {
                            val type = doc.getString("type") ?: "word"
                            val text = doc.getString("text") ?: ""
                            val translation = doc.getString("translation") ?: ""
                            allBookmarks.add(Bookmark(doc.id, type, text, translation))
                        }
                        filterBookmarks("all")
                    }
                }
        } else {
            Toast.makeText(this, "Вы не вошли в аккаунт", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteBookmark(bookmark: Bookmark) {
        val user = auth.currentUser ?: return
        db.collection("users").document(user.uid)
            .collection("bookmarks")
            .document(bookmark.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ошибка удаления", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openBookmark(bookmark: Bookmark) {
        when (bookmark.type) {
            "word" -> {
                val intent = Intent(this, WordsActivity::class.java)
                intent.putExtra("word", bookmark.text)
                startActivity(intent)
            }
            "flashcard" -> {
                val intent = Intent(this, FlashcardsActivity::class.java)
                intent.putExtra("flashcardText", bookmark.text)
                startActivity(intent)
            }
            "grammar" -> {
                val intent = Intent(this, GrammarActivity::class.java)
                intent.putExtra("grammarText", bookmark.text)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        listener?.remove()
        super.onDestroy()
    }
}
