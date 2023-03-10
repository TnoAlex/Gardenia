package com.alex.gardenia.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.alex.gardenia.R


class CalendarListAdapter(val context: Context, private val list: List<Pair<String, String>>) :
    RecyclerView.Adapter<CalendarListAdapter.RecyclerHolder>() {

    inner class RecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {
        var weekText: TextView
        var dateText: TextView

        init {
            weekText = view.findViewById(R.id.calendar_week)
            dateText = view.findViewById(R.id.calendar_date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.patient_calendar_list_litem, parent, false)
        val holder = RecyclerHolder(view)
        holder.setIsRecyclable(false)
        return holder
    }


    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.weekText.text = list[position].first
        holder.dateText.text = list[position].second
        val resources = holder.itemView.resources
        if (position != 1 && position != 0) {
            holder.dateText.setTextColor(resources.getColor(R.color.black))
            holder.weekText.setTextColor(resources.getColor(R.color.gray_dark))
            (holder.itemView as CardView).setCardBackgroundColor(resources.getColor(R.color.white))
        }
        if (position == 0) {
            (holder.itemView as CardView).setCardBackgroundColor(resources.getColor(R.color.pink))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}