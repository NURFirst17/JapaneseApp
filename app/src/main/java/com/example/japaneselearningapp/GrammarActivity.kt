package com.example.japaneselearningapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class GrammarRule(val title: String, val description: String)

class GrammarActivity : AppCompatActivity() {

    private val grammarRules = listOf(
        GrammarRule("Частицы は и が",
            "は используется для темы, が для подлежащего.\nПример: 猫はかわいいです。"),
        GrammarRule("Простые глаголы",
            "Глаголы в настоящем времени имеют окончание ます.\nПример: 食べます – есть"),
        GrammarRule("Прилагательные",
            "い-прилагательные и な-прилагательные отличаются окончанием.\nПример: 高い – высокий, きれいな – красивый"),
        GrammarRule("Вопросительные предложения",
            "Для вопросов используем か в конце.\nПример: あなたは学生ですか？"),
        GrammarRule("Отрицательные предложения",
            "Для отрицания добавляем ません после глагола.\nПример: 食べません – не есть"),
        GrammarRule("Прошедшее время глаголов",
            "Добавляем ました для прошедшего времени.\nПример: 食べました – ел"),
        GrammarRule("Прилагательные в прошедшем времени",
            "い-прилагательные: かった\nな-прилагательные: でした\nПример: 高かった, きれいでした"),
        GrammarRule("Составные предложения",
            "Используем て форму глагола для соединения действий.\nПример: 食べて、寝ます – поел и сплю"),
        GrammarRule("Сравнения",
            "Используем より и ほう для сравнения.\nПример: 猫は犬より小さいです – Кот меньше, чем собака"),
        GrammarRule("Выражение желания",
            "Добавляем たい к основе глагола.\nПример: 食べたい – хочу есть")
    )

    private var index = 0

    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var counterText: TextView
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar)

        titleText = findViewById(R.id.titleText)
        descriptionText = findViewById(R.id.descriptionText)
        counterText = findViewById(R.id.counterText)
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)

        updateRule()

        nextButton.setOnClickListener {
            index = (index + 1) % grammarRules.size
            updateRule()
        }

        prevButton.setOnClickListener {
            index = if (index - 1 < 0) grammarRules.size - 1 else index - 1
            updateRule()
        }
    }

    private fun updateRule() {
        val rule = grammarRules[index]
        titleText.text = rule.title
        descriptionText.text = rule.description
        counterText.text = "${index + 1} / ${grammarRules.size}"
    }
}
