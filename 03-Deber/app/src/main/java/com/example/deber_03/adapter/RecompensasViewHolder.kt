package com.example.deber_03.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deber_03.Recompensas
import com.example.deber_03.databinding.ItemRecompensaBinding

class RecompensasViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val binding = ItemRecompensaBinding.bind(view)

    fun render(modeloRecompensas: Recompensas, onClickListener: (Recompensas) -> Unit){
        binding.tvItemRecompensaNombre.text = modeloRecompensas.nombreRecompensa
        binding.tvItemRecompensaMonedas.text = modeloRecompensas.cantidadMonedas.toString()
        Glide.with(binding.ivItemRecompensa.context).load(modeloRecompensas.imagenRecompesa).into(binding.ivItemRecompensa)

        itemView.setOnClickListener{onClickListener(modeloRecompensas)}

    }
}