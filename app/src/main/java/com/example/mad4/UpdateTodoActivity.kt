package com.example.mad4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad4.databinding.ActivityUpdateBinding

class UpdateTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: TodoDatabaseHelper
    private var todoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabaseHelper(this)

        todoId = intent.getIntExtra("todo_id", -1)
        if (todoId == -1) {
            finish()
            return
        }

        val todo = db.getTodoById(todoId)
        if (todo != null) {
            binding.updatetitleEditText.setText(todo.title)
            binding.updatecontentEditText.setText(todo.content)

            binding.updatesaveButton.setOnClickListener {
                val updatedTitle = binding.updatetitleEditText.text.toString()
                val updatedContent = binding.updatecontentEditText.text.toString()
                val updatedTodo = ToDo(todoId, updatedTitle, updatedContent)
                db.updateTodo(updatedTodo)
                finish()
                Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error: Todo not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
