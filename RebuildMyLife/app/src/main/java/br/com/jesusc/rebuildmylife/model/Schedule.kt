package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.enums.EnumSchedule

class Schedule {
    private val scheduleList = ArrayList<Int>()

    companion object

    fun addSchedule(dayWeek:EnumSchedule){
        scheduleList.add(dayWeek.value)
    }

    fun removeSchedule(dayWeek:EnumSchedule){
        if (scheduleList.contains(dayWeek.value)) {
            // Remover o elemento
            scheduleList.remove(dayWeek.value)
        }
    }

    fun getList():ArrayList<Int>{
        return scheduleList
    }
}