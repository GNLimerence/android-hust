package com.example.bai2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentList = listOf(
            Student("Pham Hoang Hai Nam", "20215099"),
            Student("Luu Viet Hoan", "20215057"),
            Student("Ha Dinh Nam", "20215095"),
            Student("Vu Minh Quan", "20215182")
        )

        val rvStudentList = findViewById<RecyclerView>(R.id.rvStudentList)
        rvStudentList.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter(studentList)
        rvStudentList.adapter = studentAdapter

        val etSearch = findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString()
                filterStudentList(keyword)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterStudentList(keyword: String) {
        val filteredList = if (keyword.length > 2) {
            studentList.filter {
                it.name.contains(keyword, ignoreCase = true) ||
                        it.mssv.contains(keyword)
            }
        } else {
            studentList
        }

        studentAdapter.updateData(filteredList)
    }
}
