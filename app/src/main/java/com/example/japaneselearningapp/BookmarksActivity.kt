package com.example.japaneselearningapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookmarksActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val bookmarks = mutableListOf<String>()
    private lateinit var adapter: BookmarksAdapter

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        recyclerView = findViewById(R.id.bookmarksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BookmarksAdapter(bookmarks)
        recyclerView.adapter = adapter

        loadBookmarks()
    }

    private fun loadBookmarks() {
        db.collection("users")
            .document(currentUser.uid)
            .collection("bookmarks")
            .get()
            .addOnSuccessListener { result ->
                bookmarks.clear()
                for (doc in result) {
                    doc.getString("word")?.let { bookmarks.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Ошибка загрузки: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}