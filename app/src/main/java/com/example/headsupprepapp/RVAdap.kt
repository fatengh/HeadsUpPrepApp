package com.example.headsupprepapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*


class RVAdap (private val context: Context, private var celebs: ArrayList<Celebrity>): RecyclerView.Adapter<RVAdap.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val celebrity = celebs[position]

        holder.itemView.apply {
            tvName.text = celebrity.name
            tvT1.text = celebrity.taboo1
            tvT2.text = celebrity.taboo2
            tvT3.text = celebrity.taboo3
        }
    }

    override fun getItemCount() = celebs.size

    fun update(celebrities: ArrayList<Celebrity>){
        this.celebs = celebrities
        notifyDataSetChanged()
    }
}