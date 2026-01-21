package br.com.jesusc.rebuildmylife.model

import android.os.Parcelable


data class UiDate(
    var date: Long,
    var dayOfWeek: Int,
    var isSelected: Boolean = false
)