package Eric.latihann

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class home : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList: MutableList<Task> = mutableListOf()
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        fabAdd = view.findViewById(R.id.fab_add) // Inisialisasi di sini
        fabAdd.setOnClickListener {
            navigateToAdd() // Panggil method navigasi saat tombol diklik
        }
        recyclerView = view.findViewById(R.id.recycler_view)
        taskList = TaskPreferences.loadTasks(requireContext())
        taskAdapter = TaskAdapter(
            taskList,
            onEdit = { task -> navigateToEdit(task) },
            onDelete = { position -> deleteTask(position) },
            onStatusChange = { position -> changeTaskStatus(position) },
            onSaveToPrefs = { task ->
                val position = taskList.indexOf(task)
                if (position >= 0) {
                    saveAndRemoveTask(position)
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = taskAdapter
        return view
    }

    fun addTask(newTask: Task) {
        taskList.add(newTask)
        taskAdapter.notifyItemInserted(taskList.size - 1)
        TaskPreferences.saveTasks(requireContext(), taskList)
        taskAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Task berhasil ditambahkan", Toast.LENGTH_SHORT).show()
    }

    fun updateTask(oldTask: Task, updatedTask: Task) {
        val index = taskList.indexOf(oldTask)
        if (index >= 0) {
            taskList[index] = updatedTask
            taskAdapter.notifyItemChanged(index)
            TaskPreferences.saveTasks(requireContext(), taskList)
        }
    }

    private fun deleteTask(position: Int) {
        if (position in taskList.indices) {
            taskList.removeAt(position)
            taskAdapter.notifyItemRemoved(position)
            TaskPreferences.saveTasks(requireContext(), taskList)
        } else {
            Toast.makeText(requireContext(), "Task tidak ditemukan!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeTaskStatus(position: Int) {
        val task = taskList[position]
        when (task.status) {
            "To Do" -> {
                task.status = "In Progress"
            }
            "In Progress" -> {
                task.status = "Done"
            }
        }
        taskAdapter.notifyItemChanged(position)
        TaskPreferences.saveTasks(requireContext(), taskList)
    }

    private fun saveToPreferences(task: Task) {
        TaskPreferences.saveTasks(requireContext(), taskList)
        Toast.makeText(requireContext(), "Task disimpan ke Shared Preferences!", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToAdd() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, add_edit())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToEdit(task: Task) {
        val fragment = add_edit()
        val bundle = Bundle()
        bundle.putParcelable("task", task)
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun saveAndRemoveTask(position: Int) {
        val task = taskList[position]
        Toast.makeText(requireContext(), "Task '${task.name}' disimpan ke Shared Preferences!", Toast.LENGTH_SHORT).show()
        deleteTask(position)
    }
}
