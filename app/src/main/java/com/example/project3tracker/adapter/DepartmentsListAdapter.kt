package com.example.project3tracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project3tracker.R
import com.example.project3tracker.api.model.GetDepartmentsResponse

class DepartmentsListAdapter(
    private var list: ArrayList<GetDepartmentsResponse>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<DepartmentsListAdapter.SimpleDataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class SimpleDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val departmentsTitleView: TextView = itemView.findViewById(R.id.department_name_view)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.departments_item, parent, false)

        return SimpleDataViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    fun setData(newList: ArrayList<GetDepartmentsResponse>) {
        list = newList
    }

    override fun onBindViewHolder(
        holder: SimpleDataViewHolder,
        position: Int
    ) {
        val currentItem = list[position]
        holder.departmentsTitleView.text = currentItem.name
    }

}