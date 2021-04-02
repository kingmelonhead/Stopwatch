package com.example.stopwatch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.output_template.view.*

class RecyclerAdapter(private val item_list: List<ExampleOutput>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    //

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.output_template, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = item_list[position]
        holder.time_item.text = item.lap_time
        holder.lap_item.text = item.lap_num.toString()


    }

    override fun getItemCount(): Int {
            return item_list.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var time_item : TextView = itemView.text_location
        var lap_item : TextView = itemView.lap_location
    }
}