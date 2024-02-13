package com.example.deber_03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.R
import com.example.deber_03.Recompensas

class RecompensasAdapter ( private val listaRecompensas: List<Recompensas>, private val onClickListener: (Recompensas) -> Unit  ) : RecyclerView.Adapter<RecompensasViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecompensasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecompensasViewHolder(layoutInflater.inflate(R.layout.item_recompensa, parent,false))
    }

    override fun getItemCount(): Int =  listaRecompensas.size


    override fun onBindViewHolder(holder: RecompensasViewHolder, position: Int) {
        val item = listaRecompensas[position]
        holder.render(item, onClickListener)

    }
}