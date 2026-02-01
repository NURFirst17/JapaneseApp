package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class GrammarListActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GrammarAdapter
    private val grammarList = mutableListOf<GrammarItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar_list)

        recyclerView = findViewById(R.id.grammarRecyclerView)
        adapter = GrammarAdapter(grammarList) { item ->
            val intent = Intent(this, GrammarDetailActivity::class.java)
            intent.putExtra("grammarId", item.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadGrammar()
    }

    private fun loadGrammar() {
        db.collection("grammar").get().addOnSuccessListener { result ->
            grammarList.clear()
            for (doc in result) {
                val item = GrammarItem(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    rule = doc.getString("rule") ?: "",
                    example = doc.getString("example") ?: "",
                    translation = doc.getString("translation") ?: ""
                )
                grammarList.add(item)
            }
            adapter.notifyDataSetChanged()
        }
    }
}
