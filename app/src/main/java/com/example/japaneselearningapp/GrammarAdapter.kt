package com.example.japaneselearningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GrammarAdapter(
    private val list: List<GrammarItem>,
    private val clickListener: (GrammarItem) -> Unit
) : RecyclerView.Adapter<GrammarAdapter.GrammarViewHolder>() {

    class GrammarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.grammarTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrammarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grammar, parent, false)
        return GrammarViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrammarViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount() = list.size
}
