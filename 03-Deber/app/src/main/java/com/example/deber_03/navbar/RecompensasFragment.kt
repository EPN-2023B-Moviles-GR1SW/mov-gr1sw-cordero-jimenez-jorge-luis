package com.example.deber_03.navbar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deber_03.R
import com.example.deber_03.Recompensas
import com.example.deber_03.RecompensasProvider
import com.example.deber_03.RecompensasProvider.Companion.listaRecompensas
import com.example.deber_03.adapter.RecompensasAdapter
import com.example.deber_03.databinding.FragmentRecompensasBinding


class RecompensasFragment  : Fragment()  {

    private var _binding:  FragmentRecompensasBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecompensasBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){

        val manager = GridLayoutManager(requireContext(), 3)
        val decoration = DividerItemDecoration(requireContext(), manager.orientation)

        binding.rvRecompensas.layoutManager = manager
        binding.rvRecompensas.adapter =RecompensasAdapter(RecompensasProvider.listaRecompensas){
            recompensa -> onItemSelected(recompensa)
        }
        binding.rvRecompensas.addItemDecoration(decoration)

    }

    fun onItemSelected(recompensas: Recompensas){
        Toast.makeText(requireContext(),recompensas.cantidadMonedas.toString(),Toast.LENGTH_SHORT).show()
    }


}