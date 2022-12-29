package com.example.project3tracker.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project3tracker.R
import com.example.project3tracker.api.model.GetProfileResponse
import com.example.project3tracker.api.model.TaskResponse

class TasksListAdapter(
    private var list: ArrayList<TaskResponse>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener,
) :
    RecyclerView.Adapter<TasksListAdapter.SimpleDataViewHolder>() {

    private var user: GetProfileResponse ?= null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    open inner class SimpleDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        override fun onLongClick(v: View?): Boolean {
            TODO("Not yet implemented")
        }
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    inner class DataViewHolder(itemView: View) : SimpleDataViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val taskTitleTextView: TextView = itemView.findViewById(R.id.task_title_view)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.task_description_view)
        val taskPriorityTextView: TextView = itemView.findViewById(R.id.task_priority_view)
        val taskOwnerProfileImage: ImageView =
            itemView.findViewById(R.id.task_owner_profile_image_view)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataViewHolder {
        return when (viewType) {
            TaskListItemType.SIMPLE.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.simple_task_list_item, parent, false)
                SimpleDataViewHolder(itemView)
            }
            TaskListItemType.COMPLEX.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_list_item, parent, false)
                DataViewHolder(itemView)
            }
            else -> {
                throw IllegalStateException("Type is not supported!")
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentItem = list[position]

        return if (currentItem.status == 0) {
            TaskListItemType.COMPLEX.value
        } else {
            TaskListItemType.COMPLEX.value
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SimpleDataViewHolder, position: Int) {
        if (getItemViewType(position) == TaskListItemType.COMPLEX.value) {
            val complexHolder = (holder as DataViewHolder)
            val currentItem = list[position]
            if(currentItem.status == 0){
                complexHolder.taskTitleTextView.text = currentItem.title+"\n(DONE)"
            }
            else{
                complexHolder.taskTitleTextView.text = currentItem.title
            }

            complexHolder.taskDescriptionTextView.text = currentItem.description

            when (currentItem.priority) {
                0 -> {
                    complexHolder.taskPriorityTextView.setBackgroundColor(Color.RED)
                }
                1 -> {
                    complexHolder.taskPriorityTextView.setBackgroundColor(Color.YELLOW)
                }
                2 -> {
                    complexHolder.taskPriorityTextView.setBackgroundColor(Color.GREEN)
                }
            }

            Glide.with(context)
                .load(user?.image)
                .error(R.drawable.ic_baseline_house_24)
                .override(100, 100)
                .into(complexHolder.taskOwnerProfileImage)
        }
    }

    override fun getItemCount() = list.size

    fun setData(newList: ArrayList<TaskResponse>) {
        list = newList
    }

    fun setUser(user: GetProfileResponse){
        this.user = user
    }

    private enum class TaskListItemType(val value: Int) {
        SIMPLE(0),
        COMPLEX(1)
    }
}