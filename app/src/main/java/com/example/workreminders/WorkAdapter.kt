package com.example.workreminders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkAdapter(private val list: List<Work>) : RecyclerView.Adapter<WorkAdapter.WorkViewHolder>() {
    inner class WorkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val TV_workname: TextView
        val TV_worktime: TextView

        init {
            TV_workname = view.findViewById(R.id.TV_work_name)
            TV_worktime = view.findViewById(R.id.TV_work_time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkAdapter.WorkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.work_item, parent, false)
        return WorkViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkAdapter.WorkViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = list.size
}