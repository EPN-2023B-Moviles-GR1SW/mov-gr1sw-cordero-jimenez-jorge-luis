package com.example.deber_03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deber_03.adapter.RecompensasAdapter
import com.example.deber_03.databinding.ActivityMainBinding
import com.example.deber_03.navbar.HabitosFragment
import com.example.deber_03.navbar.RecompensasFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener



class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener,
    NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.setOnItemSelectedListener(this)


    }

    private fun abrirFragmento(fragmento: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragmento)
        transaction.commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.im_habitos -> {
                // Muestra el fragmento de Hábitos
                abrirFragmento(HabitosFragment())
                return true
            }
            R.id.im_recompensas -> {
                // Muestra el fragmento de Recompensas
                abrirFragmento(RecompensasFragment())
                return true
            }
            else -> return false
        }
    }





}
