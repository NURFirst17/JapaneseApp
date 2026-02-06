package com.example.japaneselearningapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

data class Kana(val symbol: String, val romaji: String)

class AlphabetActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var symbolText: TextView
    private lateinit var romajiText: TextView
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var speakButton: Button
    private lateinit var bookmarkButton: Button

    private val hiragana = listOf(
        Kana("あ","a"), Kana("い","i"), Kana("う","u"), Kana("え","e"), Kana("お","o"),
        Kana("か","ka"), Kana("き","ki"), Kana("く","ku"), Kana("け","ke"), Kana("こ","ko"),
        Kana("さ","sa"), Kana("し","shi"), Kana("す","su"), Kana("せ","se"), Kana("そ","so"),
        Kana("た","ta"), Kana("ち","chi"), Kana("つ","tsu"), Kana("て","te"), Kana("と","to"),
        Kana("な","na"), Kana("に","ni"), Kana("ぬ","nu"), Kana("ね","ne"), Kana("の","no"),
        Kana("は","ha"), Kana("ひ","hi"), Kana("ふ","fu"), Kana("へ","he"), Kana("ほ","ho"),
        Kana("ま","ma"), Kana("み","mi"), Kana("む","mu"), Kana("め","me"), Kana("も","mo"),
        Kana("や","ya"), Kana("ゆ","yu"), Kana("よ","yo"),
        Kana("ら","ra"), Kana("り","ri"), Kana("る","ru"), Kana("れ","re"), Kana("ろ","ro"),
        Kana("わ","wa"), Kana("を","wo"),
        Kana("ん","n")
    )

    private var index = 0
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alphabet)

        tts = TextToSpeech(this, this)

        symbolText = findViewById(R.id.symbolText)
        romajiText = findViewById(R.id.romajiText)
        nextButton = findViewById(R.id.nextButton)
        prevButton = findViewById(R.id.prevButton)
        speakButton = findViewById(R.id.speakButton)

        updateKana()

        nextButton.setOnClickListener {
            index = (index + 1) % hiragana.size
            updateKana()
        }

        prevButton.setOnClickListener {
            index = if (index - 1 < 0) hiragana.size - 1 else index - 1
            updateKana()
        }

        speakButton.setOnClickListener {
            val kana = hiragana[index]
            tts.speak(kana.romaji, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun updateKana() {
        val kana = hiragana[index]
        symbolText.text = kana.symbol
        romajiText.text = kana.romaji
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.JAPANESE)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts.setLanguage(Locale.US)
            }
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
