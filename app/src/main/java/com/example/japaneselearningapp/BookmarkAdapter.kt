package com.example.japaneselearningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookmarksAdapter(
    private val bookmarks: MutableList<String>
) : RecyclerView.Adapter<BookmarksAdapter.BookmarkViewHolder>() {

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser!!

    inner class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookmarkText: TextView = itemView.findViewById(R.id.bookmarkText)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmark, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val word = bookmarks[position]
        holder.bookmarkText.text = word

        holder.deleteButton.setOnClickListener {
            // Удаляем из Firestore
            db.collection("users")
                .document(currentUser.uid)
                .collection("bookmarks")
                .whereEqualTo("word", word)
                .get()
                .addOnSuccessListener { result ->
                    for (doc in result) {
                        db.collection("users")
                            .document(currentUser.uid)
                            .collection("bookmarks")
                            .document(doc.id)
                            .delete()
                            .addOnSuccessListener {
                                bookmarks.removeAt(position)
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, bookmarks.size)
                                Toast.makeText(
                                    holder.itemView.context,
                                    "\"$word\" удалено",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
        }
    }

    override fun getItemCount(): Int = bookmarks.size
}
