package br.com.jesusc.rebuildmylife.util

import br.com.jesusc.rebuildmylife.model.Task

interface CallbackTask {
    fun taskChecked(task: Task)
    fun taskUnChecked(task: Task)
    fun taskDeleted(task: Task)
    fun taskUpdated(task: Task)
    fun notifyDataSetChanged()
    fun taskClicked(task: Task)
}





