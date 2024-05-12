package com.example.mad4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mad4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TodoDatabaseHelper
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabaseHelper(this)
        todoAdapter = TodoAdapter(db.getAllTodo(), this)

        binding.todoRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.todoRecyclerView.adapter = todoAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        todoAdapter.refreshData(db.getAllTodo())
    }
}
