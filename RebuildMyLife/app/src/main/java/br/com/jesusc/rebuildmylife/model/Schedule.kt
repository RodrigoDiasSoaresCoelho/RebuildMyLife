package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.enums.EnumSchedule

class Schedule {
    private val scheduleList = ArrayList<EnumSchedule>()

    companion object

    fun addSchedule(dayWeek:EnumSchedule){
        scheduleList.add(dayWeek)
    }

    fun removeSchedule(dayWeek:EnumSchedule){
        if (scheduleList.contains(dayWeek)) {
            // Remover o elemento
            scheduleList.remove(dayWeek)
        }
    }

    fun getList():ArrayList<EnumSchedule>{
        return scheduleList
    }
}