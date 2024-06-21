package com.example.sigizi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sigizi.R
import com.example.sigizi.data.response.Child
import com.example.sigizi.view.history.HistoryDetailActivity
import com.google.android.material.button.MaterialButton

class HistoryAdapter(
    private val children: MutableList<Child>,
    private val onDeleteClickListener: (Child) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ChildViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardbayi, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val child = children[position]
        holder.bind(child)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, HistoryDetailActivity::class.java).apply {
                putExtra("EXTRA_NAME", child.name)
                putExtra("EXTRA_AGE", child.age)
                putExtra("EXTRA_GENDER", child.gender)
                putExtra("EXTRA_WEIGHT", child.weight)
                putExtra("EXTRA_HEIGHT", child.height)
                putExtra("EXTRA_LK", child.lk)
                putExtra("EXTRA_LABEL", child.label)
                putExtra("EXTRA_IDEAL", child.ideal)
                putExtra("EXTRA_SUGGESTION", child.suggestion) // Add suggestion here
            }
            context.startActivity(intent)
        }
        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClickListener.invoke(child)
        }
    }

    override fun getItemCount(): Int = children.size

    fun updateData(newList: List<Child>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return children.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return children[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return children[oldItemPosition] == newList[newItemPosition]
            }
        })

        children.clear()
        children.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.textname)
        private val textLabel: TextView = itemView.findViewById(R.id.label)
        val deleteButton: MaterialButton = itemView.findViewById(R.id.deleteButton)

        fun bind(child: Child) {
            textName.text = child.name
            textLabel.text = child.label
        }
    }
}
