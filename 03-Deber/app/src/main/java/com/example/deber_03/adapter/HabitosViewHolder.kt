package com.example.deber_03.adapter

import android.content.DialogInterface.OnClickListener
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deber_03.Habitos
import com.example.deber_03.databinding.ItemHabitosBinding

class HabitosViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val binding = ItemHabitosBinding.bind(view)

    fun render(modeloHabitos: Habitos, onClickListener: (Habitos) -> Unit){
        binding.tvHabitosTitulo.text = modeloHabitos.tituloHabito
        binding.tvHabitosFecha.text = modeloHabitos.fechaCreacion.toString()
        binding.tvHabitosDescripcion.text = modeloHabitos.descripcion
        Glide.with(binding.ivItemHabitos.context).load(modeloHabitos.estadoHabito).into(binding.ivItemHabitos)

        itemView.setOnClickListener{onClickListener(modeloHabitos)}
    }
}