package Eric.latihann

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskPreferences {

    private const val PREF_NAME = "TaskPrefs"
    private const val TASK_KEY = "task_list"

    fun saveTasks(context: Context, taskList: List<Task>) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(taskList)
        editor.putString(TASK_KEY, json)
        editor.apply()
    }

    fun loadTasks(context: Context): MutableList<Task> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(TASK_KEY, null)
        val type = object : TypeToken<MutableList<Task>>() {}.type
        return if (json != null) gson.fromJson(json, type) else mutableListOf()
    }
}