package com.example.sigizi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sigizi.R
import com.example.sigizi.data.pref.DetailNutrisi
import com.example.sigizi.data.pref.Nutrisi
import com.example.sigizi.view.nutrisi.DetailNutrisiActivity

class NutrisiAdapter(private val nutrisiList: List<DetailNutrisi>) :
    RecyclerView.Adapter<NutrisiAdapter.NutrisiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrisiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_nutrisi, parent, false)
        return NutrisiViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nutrisiList.size
    }

    override fun onBindViewHolder(holder: NutrisiViewHolder, position: Int) {
        if (position < nutrisiList.size) { // Periksa apakah position valid
            val nutrisi = nutrisiList[position]
            holder.bind(nutrisi)
        } else {
            // Tangani kondisi di luar batas
        }
    }

    inner class NutrisiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val umurTextView: TextView = itemView.findViewById(R.id.umur)

        fun bind(nutrisi: DetailNutrisi) {
            umurTextView.text = nutrisi.umur

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailNutrisiActivity::class.java).apply {
                    putExtra("EXTRA_TITLE", nutrisi.umur)
                    putExtra("EXTRA_DESCRIPTION", nutrisi.nutrisi)
                    putExtra("EXTRA_FOOD", nutrisi.makanan)
                }
                context.startActivity(intent)
            }
        }
    }
}