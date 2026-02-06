package com.example.japaneselearningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookmarksAdapter(
    private val bookmarks: List<Bookmark>,
    private val onDeleteClick: (Bookmark) -> Unit,
    private val onItemClick: (Bookmark) -> Unit
) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.bookmarkText)
        val translationView: TextView = itemView.findViewById(R.id.bookmarkTranslation)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bookmarks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.textView.text = bookmark.text
        holder.translationView.text = bookmark.translation

        holder.deleteButton.setOnClickListener { onDeleteClick(bookmark) }
        holder.itemView.setOnClickListener { onItemClick(bookmark) }
    }
}
