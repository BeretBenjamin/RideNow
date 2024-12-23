package com.example.ridenow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ridenow.R
import com.example.ridenow.model.Motorcycle
import android.content.Context

class MotorcycleAdapter(
    private var motorcycles: List<Motorcycle>,
    private val onEditClick: (Motorcycle) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val context: Context // Pass context to access resources
) : RecyclerView.Adapter<MotorcycleAdapter.MotorcycleViewHolder>() {

    inner class MotorcycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val motorcycleName: TextView = itemView.findViewById(R.id.motorcycle_name)
        private val editButton: TextView = itemView.findViewById(R.id.update_button) // TextView for "EDIT"
        private val deleteButton: TextView = itemView.findViewById(R.id.delete_button) // TextView for "DEL"

        fun bind(motorcycle: Motorcycle) {
            // Set the motorcycle name with a description format
            motorcycleName.text = context.getString(
                R.string.motorcycle_description,
                motorcycle.make,
                motorcycle.model,
                motorcycle.year
            )
            // Set click listeners for edit and delete actions
            editButton.setOnClickListener {
                onEditClick(motorcycle)
            }
            deleteButton.setOnClickListener {
                onDeleteClick(motorcycle.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotorcycleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_motorcycle, parent, false)
        return MotorcycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: MotorcycleViewHolder, position: Int) {
        holder.bind(motorcycles[position])
    }

    override fun getItemCount(): Int = motorcycles.size

    fun updateMotorcycles(newMotorcycles: List<Motorcycle>) {
        motorcycles = newMotorcycles
        notifyDataSetChanged() // Notify the adapter to refresh the list
    }
}
