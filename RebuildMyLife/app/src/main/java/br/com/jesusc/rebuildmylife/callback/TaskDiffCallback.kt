package br.com.jesusc.rebuildmylife.callback

import androidx.recyclerview.widget.DiffUtil
import br.com.jesusc.rebuildmylife.model.Task

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
