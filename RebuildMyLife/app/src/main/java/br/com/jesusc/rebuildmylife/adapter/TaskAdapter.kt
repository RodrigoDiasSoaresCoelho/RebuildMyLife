package br.com.jesusc.rebuildmylife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.enums.EnumPriority
import br.com.jesusc.rebuildmylife.model.Task
import br.com.jesusc.rebuildmylife.model.UiDate
import br.com.jesusc.rebuildmylife.util.CallbackTask

class TaskAdapter (private val tasks: MutableList<Task>, val callbackTask: CallbackTask) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskName)
        val checkboxAdapter: ImageView = itemView.findViewById(R.id.checkboxAdapter)
        val checkboxCircle: View = itemView.findViewById(R.id.checkboxCircle)
        val taskContainer: ConstraintLayout = itemView.findViewById(R.id.taskContainer)
        val checkboxContainer: ConstraintLayout = itemView.findViewById(R.id.checkboxContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskTitle.text = task.title
        setImg(task, holder)

        holder.checkboxContainer.setOnClickListener {
            task.checked = !task.checked
            setImg(task, holder)

            callbackTask.taskChecked(task)
        }

        val priorityColor = setColor(task)

        holder.taskContainer.setBackgroundResource(priorityColor)

        holder.taskContainer.setOnClickListener {
            callbackTask.taskClicked(task) }

    }

    override fun getItemCount() = tasks.size

    private fun setColor(task: Task): Int {
        return when (task.enumPriority) {
            EnumPriority.URGENT -> if(!task.checked) R.drawable.priority_urgent else R.drawable.priority_urgent_checked
            EnumPriority.HIGH -> if(!task.checked) R.drawable.priority_high else R.drawable.priority_high_checked
            EnumPriority.MEDIUM -> if(!task.checked) R.drawable.priority_medium else R.drawable.priority_medium_checked
            EnumPriority.LOW -> if(!task.checked) R.drawable.priority_low else R.drawable.priority_low_checked
        }
    }

    private fun setImg(task: Task, holder: TaskViewHolder){
        if(task.checked) {
            holder.checkboxAdapter.visibility = View.VISIBLE
            holder.checkboxCircle.visibility = View.GONE

        }else {
            holder.checkboxAdapter.visibility = View.GONE
            holder.checkboxCircle.visibility = View.VISIBLE
        }
    }
    fun submitList(newList: MutableList<Task>) {
        tasks.clear()
        tasks.addAll(newList)
        notifyDataSetChanged()
    }
}