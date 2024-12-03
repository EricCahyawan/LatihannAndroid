package Eric.latihann

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onEdit: (Task) -> Unit,
    private val onDelete: (Int) -> Unit,
    private val onStatusChange: (Int) -> Unit,
    private val onSaveToPrefs: (Task) -> Unit // Ubah parameter ke Int agar lebih konsisten
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder
    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.task_name)
        val btnDelete: Button = view.findViewById(R.id.btn_delete)
        val btnEdit: Button = view.findViewById(R.id.btn_edit)
        val btnStatus: Button = view.findViewById(R.id.btn_status)
        val arrowDown: ImageView = view.findViewById(R.id.arrow_down)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.btnStatus.text = when (task.status) {
            "To Do" -> "Kerjakan"
            "In Progress" -> "Selesai"
            else -> ""
        }
        holder.arrowDown.visibility = if (task.status == "Done") View.VISIBLE else View.GONE
        holder.btnEdit.visibility = if (task.status == "Done") View.GONE else View.VISIBLE
        holder.btnDelete.visibility = if (task.status == "Done") View.GONE else View.VISIBLE
        holder.btnEdit.setOnClickListener { onEdit(task) }
        holder.btnDelete.setOnClickListener { onDelete(position) }
        holder.btnStatus.setOnClickListener { onStatusChange(position) }
        holder.arrowDown.setOnClickListener {
            onSaveToPrefs(task)
            onDelete(position)
        }
    }


    override fun getItemCount() = tasks.size
}
