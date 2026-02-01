package com.example.japaneselearningapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SentenceActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private val sentenceList = mutableListOf<SentenceItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_list)

        recyclerView = findViewById(R.id.sentenceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SentenceAdapter(sentenceList) { item ->
            // Можно сделать детальный экран предложения при клике
        }
        recyclerView.adapter = adapter

        loadSentences(adapter)
    }

    private fun loadSentences(adapter: SentenceAdapter) {
        db.collection("sentences")
            .get()
            .addOnSuccessListener { result ->
                sentenceList.clear()
                for (doc in result.documents) {
                    val item = SentenceItem(
                        id = doc.id,
                        sentence = doc.getString("sentence") ?: "",
                        translation = doc.getString("translation") ?: ""
                    )
                    sentenceList.add(item)
                }
                adapter.notifyDataSetChanged()
            }
    }
}
