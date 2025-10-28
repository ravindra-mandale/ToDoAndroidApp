package com.programminginmyway.todoappnew.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.programminginmyway.todoappnew.R
import com.programminginmyway.todoappnew.data.database.roomDb.dao.TaskDao
import com.programminginmyway.todoappnew.fragments.AddNewTask
import com.programminginmyway.todoappnew.data.database.roomDb.model.TaskList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAdapter(
    private val context: Context,
    private val taskDao: TaskDao,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var taskList: MutableList<TaskList> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = taskList[position]
        holder.taskCheckBox.text = item.taskTitle
        holder.taskCheckBox.isChecked = item.status != 0

        // Avoid triggering listener during recycling
        holder.taskCheckBox.setOnCheckedChangeListener(null)
        holder.taskCheckBox.isChecked = item.status != 0

        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                val status = if (isChecked) 1 else 0
                taskDao.updateStatus(item.id, status)
            }
        }
    }

    override fun getItemCount(): Int = taskList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<TaskList>) {
        taskList = tasks.toMutableList()
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val item = taskList[position]
        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(item.id)  // delete from DB by ID
            launch(Dispatchers.Main) {
                taskList.removeAt(position)   // update local list
                notifyItemRemoved(position)   // notify adapter
            }
        }
    }

    fun editItem(position: Int) {
        val item = taskList[position]
        val bundle = Bundle().apply {
            putInt("id", item.id)
            putString("task", item.taskTitle)
            putLong("dateTime", item.dateTime.time)
        }
        val fragment = AddNewTask.newInstance()
        fragment.arguments = bundle
        fragment.show((lifecycleOwner as androidx.fragment.app.FragmentActivity).supportFragmentManager, AddNewTask.TAG)
    }

    fun getContext() = context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskCheckBox: CheckBox = view.findViewById(R.id.todoCheckBox)
    }
}
