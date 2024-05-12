package com.example.mad4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad4.databinding.ActivityAddTodoBinding

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding
    private lateinit var db: TodoDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val toDo = ToDo(0, title, content)
            try {
                db.insertToDo(toDo)
                Toast.makeText(this, "ToDo Saved", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Error saving ToDo", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.saveButton.setOnClickListener {
//            val title = binding.titleEditText.text.toString()
//            val content = binding.contentEditText.text.toString()
//            val toDo = ToDo(0, title, content)
//            db.insertToDo(toDo)
//            finish()
//            Toast.makeText(this, "ToDo Saved", Toast.LENGTH_SHORT).show()
//        }
    }
}
