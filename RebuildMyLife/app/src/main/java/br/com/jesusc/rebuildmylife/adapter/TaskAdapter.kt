package br.com.jesusc.rebuildmylife.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.enums.EnumPriority
import br.com.jesusc.rebuildmylife.model.Task

class TaskAdapter (private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskName)
        val taskTime: TextView = itemView.findViewById(R.id.item_hour)
        val priorityIndicator: View = itemView.findViewById(R.id.priorityColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskTitle.text = task.title
        holder.taskTime.text = task.hour// Adapte para exibir o horário correto

        // Configurar a cor do indicador de prioridade
        val priorityColor = when (task.enumPriority) {
            EnumPriority.HIGH -> Color.RED
            EnumPriority.MEDIUM -> Color.YELLOW
            EnumPriority.LOW -> Color.GREEN
            EnumPriority.URGENT -> TODO()
        }
        holder.priorityIndicator.setBackgroundColor(priorityColor)
    }

    override fun getItemCount() = tasks.size
}