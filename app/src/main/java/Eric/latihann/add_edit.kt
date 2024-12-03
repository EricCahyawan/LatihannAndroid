package Eric.latihann

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class add_edit : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etDate: EditText
    private lateinit var etCategory: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSave: Button

    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit, container, false)

        etName = view.findViewById(R.id.et_name)
        etDate = view.findViewById(R.id.et_date)
        etCategory = view.findViewById(R.id.et_category)
        etDescription = view.findViewById(R.id.et_description)
        btnSave = view.findViewById(R.id.btn_save)

        task = arguments?.getParcelable("task")
        task?.let {
            etName.setText(it.name)
            etDate.setText(it.date)
            etCategory.setText(it.category)
            etDescription.setText(it.description)
        }

        btnSave.setOnClickListener { saveTask() }

        return view
    }

    private fun saveTask() {
        val name = etName.text.toString()
        val date = etDate.text.toString()
        val category = etCategory.text.toString()
        val description = etDescription.text.toString()

        val newTask = Task(name, date, category, description, task?.status ?: "To Do")
        val homeFragment = requireActivity().supportFragmentManager.findFragmentByTag("HOME_FRAGMENT") as? home
        if (homeFragment != null) {
            if (task == null) {
                homeFragment.addTask(newTask)
            } else {
                homeFragment.updateTask(task!!, newTask)
            }
        } else {
            Toast.makeText(requireContext(), "Home fragment not found", Toast.LENGTH_SHORT).show()
        }


        parentFragmentManager.popBackStack()
    }

}
