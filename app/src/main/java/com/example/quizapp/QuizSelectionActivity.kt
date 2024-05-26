package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class QuizSelectionActivity : AppCompatActivity() {

    private val quizzes = listOf(
        "Quiz 1: General Knowledge",
        "Quiz 2: Science",
        "Quiz 3: History",
        "Quiz 4: Literature"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_selection)

        val listView: ListView = findViewById(R.id.quizListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, quizzes)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("QUIZ_INDEX", position)
            startActivity(intent)
        }
    }
}
