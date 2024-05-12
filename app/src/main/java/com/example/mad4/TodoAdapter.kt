package com.example.mad4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mad4.R

class TodoAdapter(private var todoList: List<ToDo>, private val context: Context) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

        private  val db:TodoDatabaseHelper= TodoDatabaseHelper(context)

        class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
                val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
                val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
                val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
                val itemView = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false)
                return TodoViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
                val todo = todoList[position]
                holder.titleTextView.text = todo.title
                holder.contentTextView.text = todo.content

                holder.updateButton.setOnClickListener{
                        val intent = Intent(context, UpdateTodoActivity::class.java).apply {
                                putExtra("todo_id", todo.id)
                        }
                        context.startActivity(intent)
                }

                holder.deleteButton.setOnClickListener {
                        db.deleteTodo(todo.id)
                        refreshData(db.getAllTodo())
                        Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                }
        }

        override fun getItemCount(): Int = todoList.size

        fun refreshData(newTodoList: List<ToDo>) {
                todoList = newTodoList
                notifyDataSetChanged()
        }
}
