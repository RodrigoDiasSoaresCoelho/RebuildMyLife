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
import br.com.jesusc.rebuildmylife.util.CallbackTask

class TaskAdapter (private val tasks: List<Task>, val callbackTask: CallbackTask) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskName)
        val checkboxAdapter: ImageView = itemView.findViewById(R.id.checkboxAdapter)
        val taskContainer: ConstraintLayout = itemView.findViewById(R.id.taskContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskTitle.text = task.title
        setImg(task, holder)

        holder.checkboxAdapter.setOnClickListener {
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
            EnumPriority.URGENT -> if(!task.checked) R.color.URGENT else R.color.URGENT_CHECKED
            EnumPriority.HIGH -> if(!task.checked) R.color.HIGH else R.color.HIGH_CHECKED
            EnumPriority.MEDIUM -> if(!task.checked) R.color.MEDIUM else R.color.MEDIUM_CHECKED
            EnumPriority.LOW -> if(!task.checked) R.color.LOW else R.color.LOW_CHECKED
        }
    }

    private fun setImg(task: Task, holder: TaskViewHolder){
        if(task.checked)
            holder.checkboxAdapter.setImageResource(R.drawable.ic_check)
        else
            holder.checkboxAdapter.setImageResource(R.drawable.ic_square)
    }
}