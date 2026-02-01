package com.example.japaneselearningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SentenceAdapter(
    private val list: List<SentenceItem>,
    private val clickListener: (SentenceItem) -> Unit
) : RecyclerView.Adapter<SentenceAdapter.SentenceViewHolder>() {

    class SentenceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val japaneseText: TextView = view.findViewById(R.id.japaneseText)
        val translationText: TextView = view.findViewById(R.id.translationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sentence, parent, false)
        return SentenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) {
        val item = list[position]
        holder.japaneseText.text = item.sentence
        holder.translationText.text = item.translation
        holder.itemView.setOnClickListener { clickListener(item) }
    }

    override fun getItemCount() = list.size
}
