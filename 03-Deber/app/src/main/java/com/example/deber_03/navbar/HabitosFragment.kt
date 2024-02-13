package com.example.deber_03.navbar

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deber_03.HabitosProvider
import com.example.deber_03.R
import com.example.deber_03.Recompensas
import com.example.deber_03.RecompensasProvider
import com.example.deber_03.adapter.HabitosAdapter
import com.example.deber_03.adapter.RecompensasAdapter
import com.example.deber_03.databinding.FragmentHabitosBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HabitosFragment : Fragment() {

    private var _binding: FragmentHabitosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHabitosBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecyclerView(){

        val manager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(requireContext(), manager.orientation)

        binding.rvHabitos.layoutManager = manager
        binding.rvHabitos.adapter = HabitosAdapter(HabitosProvider.listaHabitos){it}
        binding.rvHabitos.addItemDecoration(decoration)

    }





}