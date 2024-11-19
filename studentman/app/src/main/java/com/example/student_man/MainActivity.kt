package com.example.student_man

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var students: MutableList<StudentModel>
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
            StudentModel("Hoàng Văn Hải", "SV007"),
            StudentModel("Bùi Thị Hạnh", "SV008"),
            StudentModel("Đinh Văn Hùng", "SV009"),
            StudentModel("Nguyễn Thị Linh", "SV010"),
            StudentModel("Phạm Văn Long", "SV011"),
            StudentModel("Trần Thị Mai", "SV012"),
            StudentModel("Lê Thị Ngọc", "SV013"),
            StudentModel("Vũ Văn Nam", "SV014"),
            StudentModel("Hoàng Thị Phương", "SV015"),
            StudentModel("Đỗ Văn Quân", "SV016"),
            StudentModel("Nguyễn Thị Thu", "SV017"),
            StudentModel("Trần Văn Tài", "SV018"),
            StudentModel("Phạm Thị Tuyết", "SV019"),
            StudentModel("Lê Văn Vũ", "SV020")
        )

        studentAdapter = StudentAdapter(students,
            onEditClick = { position -> showEditDialog(position) },
            onDeleteClick = { position -> showDeleteDialog(position) }
        )

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_student_name)
        val idEditText = dialogView.findViewById<EditText>(R.id.edit_student_id)

        AlertDialog.Builder(this)
            .setTitle("Thêm Sinh Viên Mới")
            .setView(dialogView)
            .setPositiveButton("Thêm") { _, _ ->
                val name = nameEditText.text.toString()
                val id = idEditText.text.toString()
                if (name.isNotBlank() && id.isNotBlank()) {
                    val newStudent = StudentModel(name, id)
                    students.add(newStudent)
                    studentAdapter.notifyItemInserted(students.size - 1)
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showEditDialog(position: Int) {
        val student = students[position]
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_student_name)
        val idEditText = dialogView.findViewById<EditText>(R.id.edit_student_id)

        nameEditText.setText(student.studentName)
        idEditText.setText(student.studentId)

        AlertDialog.Builder(this)
            .setTitle("Chỉnh Sửa Sinh Viên")
            .setView(dialogView)
            .setPositiveButton("Cập Nhật") { _, _ ->
                val name = nameEditText.text.toString()
                val id = idEditText.text.toString()
                if (name.isNotBlank() && id.isNotBlank()) {
                    students[position] = StudentModel(name, id)
                    studentAdapter.notifyItemChanged(position)
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showDeleteDialog(position: Int) {
        val deletedStudent = students[position]
        AlertDialog.Builder(this)
            .setTitle("Xóa Sinh Viên")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên này?")
            .setPositiveButton("Xóa") { _, _ ->
                students.removeAt(position)
                studentAdapter.notifyItemRemoved(position)

                Snackbar.make(
                    findViewById(R.id.recycler_view_students),
                    "Đã xóa sinh viên ${deletedStudent.studentName}",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Hoàn tác") {
                        students.add(position, deletedStudent)
                        studentAdapter.notifyItemInserted(position)
                    }
                    .show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}