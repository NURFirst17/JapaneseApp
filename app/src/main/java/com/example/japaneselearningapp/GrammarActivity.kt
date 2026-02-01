package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class GrammarActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private val grammarList = mutableListOf<GrammarItem>()
    private lateinit var adapter: GrammarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar)

        recyclerView = findViewById(R.id.grammarRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = GrammarAdapter(grammarList) { item ->
            // Передача выбранного элемента на экран деталей
            val intent = Intent(this, GrammarDetailActivity::class.java)
            intent.putExtra("title", item.title)
            intent.putExtra("rule", item.rule)
            intent.putExtra("example", item.example)
            intent.putExtra("translation", item.translation)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        loadGrammar()
    }

    private fun loadGrammar() {
        db.collection("grammar").get().addOnSuccessListener { result ->
            grammarList.clear()
            for (doc in result.documents) {
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
