package com.example.japaneselearningapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookmarkManager {

    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun addBookmark(word: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (userId == null) return

        val bookmark = hashMapOf(
            "word" to word,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(userId)
            .collection("bookmarks")
            .document(word)
            .set(bookmark)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun getBookmarks(onComplete: (List<String>) -> Unit, onFailure: (Exception) -> Unit) {
        if (userId == null) return

        db.collection("users")
            .document(userId)
            .collection("bookmarks")
            .get()
            .addOnSuccessListener { result ->
                val words = result.map { it.getString("word") ?: "" }
                onComplete(words)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}