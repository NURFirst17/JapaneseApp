package com.example.japaneselearningapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

data class TestItem(val type: String, val text: String, val translation: String = "")

class TestActivity : AppCompatActivity() {

    private val allItems = mutableListOf<TestItem>()

    private lateinit var questionText: TextView
    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button
    private lateinit var counterText: TextView

    private var currentIndex = 0
    private var score = 0
    private val testQuestions = mutableListOf<TestItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        questionText = findViewById(R.id.questionText)
        counterText = findViewById(R.id.counterText)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)

        // Собираем все данные приложения
        // Здесь добавляем слова, карточки, предложения и закладки
        allItems.addAll(AppData.words.map { TestItem("word", it.text, it.translation) })
        allItems.addAll(AppData.flashcards.map { TestItem("flashcard", it.text, it.translation) })
        allItems.addAll(AppData.sentences.map { TestItem("sentence", it.text, it.translation) })

        // Выбираем случайную выборку 10 вопросов
        testQuestions.addAll(allItems.shuffled().take(10))

        showQuestion()
    }

    private fun showQuestion() {
        if (currentIndex >= testQuestions.size) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("score", score)
            intent.putExtra("total", testQuestions.size)
            startActivity(intent)
            finish()
            return
        }

        val current = testQuestions[currentIndex]
        questionText.text = current.text
        counterText.text = "Вопрос ${currentIndex + 1} из ${testQuestions.size}"

        val options = mutableListOf(current.translation.ifEmpty { current.text })
        while (options.size < 4) {
            val randomItem = allItems.random()
            val randomText = randomItem.translation.ifEmpty { randomItem.text }
            if (!options.contains(randomText)) options.add(randomText)
        }

        options.shuffle()

        val buttons = listOf(option1, option2, option3, option4)
        buttons.forEachIndexed { i, btn ->
            btn.text = options[i]
            btn.setOnClickListener {
                if (options[i] == current.translation.ifEmpty { current.text }) {
                    score++
                    btn.text = "✔ ${btn.text}"
                } else {
                    btn.text = "✘ ${btn.text}"
                }
                btn.postDelayed({
                    currentIndex++
                    showQuestion()
                }, 800)
            }
        }
    }
}
