package com.example.mad4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todoapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "alltodo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_CONTENT TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertToDo(toDo: ToDo) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, toDo.title)
            put(COLUMN_CONTENT, toDo.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTodo(): List<ToDo> {
        val todoList = mutableListOf<ToDo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val todo = ToDo(id, title, content)
            todoList.add(todo)
        }

        cursor.close()
        db.close()
        return todoList
    }

    fun updateTodo(toDo: ToDo) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, toDo.title)
            put(COLUMN_CONTENT, toDo.content)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(toDo.id.toString()))
        db.close()
    }

    fun getTodoById(todoid: Int): ToDo? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(todoid.toString()))

        var todo: ToDo? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            todo = ToDo(id, title, content)
        }

        cursor.close()
        db.close()
        return todo
    }

    fun deleteTodo(todoId: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(todoId.toString()))
        db.close()
    }

}
