package com.alex.gardenia.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.alex.gardenia.R

class PatientHistoryAdapter(
    context: Context,
    private val resourceId: Int,
    private val list: List<Pair<String, String>>
) : ArrayAdapter<Pair<String, String>>(context, resourceId, list) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId, parent, false)
        val dateText = view.findViewById<TextView>(R.id.patient_history_list_item_date)
        val timeText = view.findViewById<TextView>(R.id.patient_history_list_item_value)
        dateText.text =
            context.resources.getString(R.string.single_character).format(list[position].first)
        timeText.text =
            context.resources.getString(R.string.single_character).format(list[position].second)
        return view
    }
}