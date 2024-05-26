package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectQuizButton: Button = findViewById(R.id.selectQuizButton)
        val randomQuizButton: Button = findViewById(R.id.randomQuizButton)

        selectQuizButton.setOnClickListener {
            val intent = Intent(this, QuizSelectionActivity::class.java)
            startActivity(intent)
        }

        randomQuizButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("QUIZ_INDEX", -1) // -1 indicates a random quiz
            startActivity(intent)
        }
    }
}
