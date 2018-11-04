package com.jhonatanlopes.mydictionary.ui.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jhonatanlopes.mydictionary.R
import kotlinx.android.synthetic.main.definitions_list_item.view.*

class DefinitionsAdapter(private val context: Context?,
                         private val definitions: List<String>
) : RecyclerView.Adapter<DefinitionsAdapter.DefinitonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitonViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.definitions_list_item, parent, false)
        return DefinitonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    override fun onBindViewHolder(holder: DefinitonViewHolder, position: Int) {
        val definition = definitions[position]
        holder.bind(definition)
    }

    class DefinitonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val definition: TextView = itemView.list_item_definition
        val number: TextView = itemView.list_item_number

        fun bind(definition: String) {
            this.definition.text = definition
            this.number.text = (adapterPosition + 1).toString()
        }
    }
}
