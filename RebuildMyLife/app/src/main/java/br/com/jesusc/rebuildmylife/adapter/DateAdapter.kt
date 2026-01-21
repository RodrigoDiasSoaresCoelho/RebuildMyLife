package br.com.jesusc.rebuildmylife.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.enums.EnumSchedule
import br.com.jesusc.rebuildmylife.model.UiDate
import br.com.jesusc.rebuildmylife.util.CallbackDate
import br.com.jesusc.rebuildmylife.util.ConvertData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class DateAdapter(private val uiDates: MutableList<UiDate>, val callbackDate: CallbackDate) :
    RecyclerView.Adapter<DateAdapter.TaskViewHolder>() {
    private val converter = ConvertData()

    private var selectedPosition = RecyclerView.NO_POSITION

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day)
        val dayOfWeek: TextView = itemView.findViewById(R.id.dayOfWeek)
        val dateContainer: ConstraintLayout = itemView.findViewById(R.id.dateContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val date = uiDates[position]
        holder.day.text = converter.getDay(date.date)
        holder.dayOfWeek.text = converter.toShortName(date.dayOfWeek)

        val isSelected = position == selectedPosition
        if (isSelected) {
            holder.dateContainer.setBackgroundResource(R.drawable.shape_date_selected)
            holder.day.setTextColor(Color.WHITE)
            holder.dayOfWeek.setTextColor(Color.WHITE)
        } else {
            holder.dateContainer.setBackgroundResource(R.drawable.shape_date)
            holder.day.setTextColor(Color.LTGRAY)
            holder.dayOfWeek.setTextColor(Color.LTGRAY)
        }

        holder.dateContainer.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            callbackDate.dateSelected(date)
        }
    }

    override fun getItemCount() = uiDates.size

    fun submitList(newList: MutableList<UiDate>) {
        uiDates.clear()
        uiDates.addAll(newList)
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }

    fun selectPosition(position: Int) {
        val previous = selectedPosition
        selectedPosition = position

        if (previous != RecyclerView.NO_POSITION) {
            notifyItemChanged(previous)
        }
        notifyItemChanged(position)
    }

    fun selectTodayIfExists(): UiDate {
        val todayEpochDay = LocalDate.now().toEpochDay()

        val position = uiDates.indexOfFirst {
            it.date == todayEpochDay
        }

        selectedPosition = position
        notifyItemChanged(position)
        return uiDates[position]
    }

    fun getSelectedPosition(): Int = selectedPosition

}
