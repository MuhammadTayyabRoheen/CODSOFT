package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var answersGroup: RadioGroup
    private lateinit var submitButton: Button

    private var currentQuestionIndex = 0
    private var score = 0
    private val incorrectQuestions = mutableListOf<Triple<Int, String, String>>() // Changed to Triple

    private val questions = listOf(
        "Which planet in the Solar System is the smallest?" to listOf("Pluto", "Earth", "Mercury", "Mars"),
        "What is the capital of France?" to listOf("Berlin", "Madrid", "Paris", "Rome"),
        "Which element has the chemical symbol O?" to listOf("Oxygen", "Gold", "Silver", "Hydrogen"),
        "Who wrote 'Romeo and Juliet'?" to listOf("Mark Twain", "William Shakespeare", "Charles Dickens", "Jane Austen"),
        "What is the largest ocean on Earth?" to listOf("Atlantic", "Indian", "Arctic", "Pacific"),
        "What is the largest planet in our Solar System?" to listOf("Earth", "Jupiter", "Saturn", "Neptune"),
        "Who discovered penicillin?" to listOf("Marie Curie", "Albert Einstein", "Alexander Fleming", "Isaac Newton"),
        "What is the smallest unit of life?" to listOf("Cell", "Atom", "Molecule", "Organ")
    )
    private val correctAnswers = listOf(2, 2, 0, 1, 3, 1, 2, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionTextView = findViewById(R.id.questionTextView)
        answersGroup = findViewById(R.id.answersGroup)
        submitButton = findViewById(R.id.submitButton)

        loadQuestion()

        submitButton.setOnClickListener {
            checkAnswer()
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                loadQuestion()
            } else {
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("SCORE", score)
                    putExtra("TOTAL_QUESTIONS", questions.size)
                    putExtra("INCORRECT_QUESTIONS", ArrayList(incorrectQuestions))
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadQuestion() {
        val (question, options) = questions[currentQuestionIndex]
        questionTextView.text = "Question ${currentQuestionIndex + 1}: $question"
        for (i in 0 until answersGroup.childCount) {
            (answersGroup.getChildAt(i) as RadioButton).text = options[i]
        }
        answersGroup.clearCheck()  // Clear previous selection
    }

    private fun checkAnswer() {
        val selectedOption = answersGroup.indexOfChild(findViewById(answersGroup.checkedRadioButtonId))
        if (selectedOption == correctAnswers[currentQuestionIndex]) {
            score++
        } else {
            val question = questions[currentQuestionIndex].first
            val correctAnswer = questions[currentQuestionIndex].second[correctAnswers[currentQuestionIndex]]
            incorrectQuestions.add(Triple(currentQuestionIndex + 1, question, correctAnswer)) // Store question index, question, and correct answer
        }
    }
}
