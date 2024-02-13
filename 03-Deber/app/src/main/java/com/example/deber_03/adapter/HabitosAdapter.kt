package com.example.deber_03.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.Habitos
import com.example.deber_03.R

class HabitosAdapter (
    private val listaHabitos: List<Habitos>,
    private val onClickListener: (Habitos) -> Unit
): RecyclerView.Adapter<HabitosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HabitosViewHolder(layoutInflater.inflate(R.layout.item_habitos, parent,false))
    }

    override fun getItemCount(): Int = listaHabitos.size

    override fun onBindViewHolder(holder: HabitosViewHolder, position: Int) {
        val item = listaHabitos[position]
        holder.render(item,onClickListener)
    }
}