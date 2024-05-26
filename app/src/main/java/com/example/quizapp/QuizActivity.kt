package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var subjectTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var answersGroup: RadioGroup
    private lateinit var submitButton: Button

    private var currentQuestionIndex = 0
    private var score = 0
    private val incorrectQuestions = mutableListOf<Triple<Int, String, String>>()

    private val generalKnowledgeQuestions = listOf(
        "Which planet in the Solar System is the smallest?" to listOf("Pluto", "Earth", "Mercury", "Mars"),
        "What is the capital of France?" to listOf("Berlin", "Madrid", "Paris", "Rome"),
        "What is the largest desert in the world?" to listOf("Sahara", "Gobi", "Antarctic", "Arctic"),
        "Which country is the largest by land area?" to listOf("Canada", "China", "Russia", "USA"),
        "What is the tallest mountain in the world?" to listOf("K2", "Kangchenjunga", "Lhotse", "Mount Everest"),
        "Who was the first President of the United States?" to listOf("Abraham Lincoln", "George Washington", "Thomas Jefferson", "John Adams"),
        "What is the longest river in the world?" to listOf("Amazon", "Nile", "Yangtze", "Mississippi")
    )
    private val scienceQuestions = listOf(
        "Which element has the chemical symbol O?" to listOf("Oxygen", "Gold", "Silver", "Hydrogen"),
        "Who discovered penicillin?" to listOf("Marie Curie", "Albert Einstein", "Alexander Fleming", "Isaac Newton"),
        "What is the chemical symbol for gold?" to listOf("Au", "Ag", "Pb", "Fe"),
        "Who is known as the father of modern physics?" to listOf("Albert Einstein", "Isaac Newton", "Galileo Galilei", "Niels Bohr"),
        "What is the powerhouse of the cell?" to listOf("Nucleus", "Mitochondria", "Ribosome", "Endoplasmic Reticulum"),
        "What is the hardest natural substance on Earth?" to listOf("Diamond", "Gold", "Iron", "Platinum"),
        "What is the speed of light?" to listOf("299,792,458 meters per second", "150,000,000 meters per second", "1,080,000,000 meters per second", "300,000,000 meters per second")
    )
    private val historyQuestions = listOf(
        "Who wrote 'Romeo and Juliet'?" to listOf("Mark Twain", "William Shakespeare", "Charles Dickens", "Jane Austen"),
        "What is the largest ocean on Earth?" to listOf("Atlantic", "Indian", "Arctic", "Pacific"),
        "Who was the first emperor of Rome?" to listOf("Julius Caesar", "Augustus", "Nero", "Caligula"),
        "When did World War II end?" to listOf("1941", "1945", "1939", "1950"),
        "Who discovered America?" to listOf("Christopher Columbus", "Leif Erikson", "Amerigo Vespucci", "James Cook"),
        "What year did the Titanic sink?" to listOf("1912", "1905", "1918", "1920"),
        "Who was the first woman to fly solo across the Atlantic Ocean?" to listOf("Amelia Earhart", "Harriet Quimby", "Bessie Coleman", "Jacqueline Cochran")
    )
    private val literatureQuestions = listOf(
        "What is the largest planet in our Solar System?" to listOf("Earth", "Jupiter", "Saturn", "Neptune"),
        "What is the smallest unit of life?" to listOf("Cell", "Atom", "Molecule", "Organ"),
        "Who wrote 'Pride and Prejudice'?" to listOf("Charlotte Bronte", "Jane Austen", "Emily Bronte", "Mary Shelley"),
        "What is the name of the wizarding school in Harry Potter?" to listOf("Beauxbatons", "Durmstrang", "Hogwarts", "Ilvermorny"),
        "Who wrote 'The Great Gatsby'?" to listOf("F. Scott Fitzgerald", "Ernest Hemingway", "Mark Twain", "J.D. Salinger"),
        "What is the first book of the Old Testament?" to listOf("Exodus", "Leviticus", "Genesis", "Numbers"),
        "Who wrote 'To Kill a Mockingbird'?" to listOf("Harper Lee", "George Orwell", "J.D. Salinger", "F. Scott Fitzgerald")
    )

    private val correctAnswersMap = mapOf(
        "generalKnowledge" to listOf(2, 2, 2, 2, 3, 1, 1),
        "science" to listOf(0, 2, 0, 1, 1, 0, 0),
        "history" to listOf(1, 3, 1, 1, 0, 0, 0),
        "literature" to listOf(1, 0, 1, 2, 0, 2, 0)
    )
    private var questions: List<Pair<String, List<String>>> = listOf()
    private var correctAnswers: List<Int> = listOf()
    private var quizSubject: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        subjectTextView = findViewById(R.id.subjectTextView)
        questionTextView = findViewById(R.id.questionTextView)
        answersGroup = findViewById(R.id.answersGroup)
        submitButton = findViewById(R.id.submitButton)

        val quizIndex = intent.getIntExtra("QUIZ_INDEX", -1)
        loadQuiz(quizIndex)

        subjectTextView.text = quizSubject

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
                    putExtra("QUIZ_SUBJECT", quizSubject)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadQuiz(index: Int) {
        when (index) {
            0 -> {
                questions = generalKnowledgeQuestions
                correctAnswers = correctAnswersMap["generalKnowledge"] ?: listOf()
                quizSubject = "General Knowledge"
            }
            1 -> {
                questions = scienceQuestions
                correctAnswers = correctAnswersMap["science"] ?: listOf()
                quizSubject = "Science"
            }
            2 -> {
                questions = historyQuestions
                correctAnswers = correctAnswersMap["history"] ?: listOf()
                quizSubject = "History"
            }
            3 -> {
                questions = literatureQuestions
                correctAnswers = correctAnswersMap["literature"] ?: listOf()
                quizSubject = "Literature"
            }
            else -> {
                val randomQuiz = listOf(generalKnowledgeQuestions, scienceQuestions, historyQuestions, literatureQuestions).random()
                questions = randomQuiz
                correctAnswers = when (randomQuiz) {
                    generalKnowledgeQuestions -> correctAnswersMap["generalKnowledge"] ?: listOf()
                    scienceQuestions -> correctAnswersMap["science"] ?: listOf()
                    historyQuestions -> correctAnswersMap["history"] ?: listOf()
                    literatureQuestions -> correctAnswersMap["literature"] ?: listOf()
                    else -> listOf()
                }
                quizSubject = when (randomQuiz) {
                    generalKnowledgeQuestions -> "General Knowledge"
                    scienceQuestions -> "Science"
                    historyQuestions -> "History"
                    literatureQuestions -> "Literature"
                    else -> "Random"
                }
            }
        }
    }

    private fun loadQuestion() {
        val (question, options) = questions[currentQuestionIndex]
        questionTextView.text = "Question ${currentQuestionIndex + 1}: $question"
        for (i in 0 until answersGroup.childCount) {
            (answersGroup.getChildAt(i) as RadioButton).text = options[i]
        }
        answersGroup.clearCheck() // Clear previous selection
    }

    private fun checkAnswer() {
        val selectedOption = answersGroup.indexOfChild(findViewById(answersGroup.checkedRadioButtonId))
        if (selectedOption == correctAnswers[currentQuestionIndex]) {
            score++
        } else {
            val question = questions[currentQuestionIndex].first
            val correctAnswer = questions[currentQuestionIndex].second[correctAnswers[currentQuestionIndex]]
            incorrectQuestions.add(Triple(currentQuestionIndex + 1, question, correctAnswer))
        }
    }
}
