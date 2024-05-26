package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)
        val incorrectQuestions = intent.getSerializableExtra("INCORRECT_QUESTIONS") as ArrayList<Triple<Int, String, String>>
        val incorrectAnswers = totalQuestions - score

        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val incorrectTextView: TextView = findViewById(R.id.incorrectTextView)
        val feedbackTextView: TextView = findViewById(R.id.feedbackTextView)
        val retryButton: Button = findViewById(R.id.retryButton)

        resultTextView.text = "Your Score: $score"
        incorrectTextView.text = "Incorrect Answers: $incorrectAnswers"
        feedbackTextView.text = buildFeedbackText(incorrectQuestions)

        retryButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun buildFeedbackText(incorrectQuestions: List<Triple<Int, String, String>>): String {
        val stringBuilder = StringBuilder()
        for (question in incorrectQuestions) {
            stringBuilder.append("Question ${question.first}:\n${question.second}\nCorrect Answer: ${question.third}\n\n")
        }
        return stringBuilder.toString().trim()
    }
}
