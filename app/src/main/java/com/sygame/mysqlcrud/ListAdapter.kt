package com.sygame.mysqlcrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(var list: ArrayList<NotesModel.Data>, var listener: OnAdapterListener):
    RecyclerView.Adapter<ListAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.card_design,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = list[position]
        holder.itemView.findViewById<TextView>(R.id.schreiben).text = data.note
        holder.itemView.setOnClickListener {
            listener.onUpdate(data)
        }

        holder.itemView.findViewById<ImageView>(R.id.bilderDelete).setOnClickListener {
            listener.onDelete(data)
        }
    }

    override fun getItemCount(): Int = list.size

    public fun setData(data: List<NotesModel.Data>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onUpdate(data: NotesModel.Data)
        fun onDelete(data: NotesModel.Data)
    }
}