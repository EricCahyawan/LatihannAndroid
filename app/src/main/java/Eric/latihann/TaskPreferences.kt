package Eric.latihann

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskPreferences {
    private const val PREF_NAME = "task_preferences"
    private const val TASK_LIST_KEY = "task_list"

    fun saveTasks(context: Context, taskList: MutableList<Task>) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val taskListJson = Gson().toJson(taskList)
        sharedPreferences.edit().putString(TASK_LIST_KEY, taskListJson).apply()
        Log.d("TaskPreferences", "Tasks saved: $taskListJson")
    }

    fun loadTasks(context: Context): MutableList<Task> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val taskListJson = sharedPreferences.getString(TASK_LIST_KEY, "[]")
        Log.d("TaskPreferences", "Tasks loaded: $taskListJson")
        return Gson().fromJson(taskListJson, Array<Task>::class.java).toMutableList()
    }
}