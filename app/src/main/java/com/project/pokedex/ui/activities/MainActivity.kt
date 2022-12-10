package com.project.pokedex.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.pokedex.R
import com.project.pokedex.databinding.ActivityMainBinding
import com.project.pokedex.ui.fragments.RegionsFragment
import com.project.pokedex.ui.fragments.TeamsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val regionsFragment = RegionsFragment()
        val teamsFragment = TeamsFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, RegionsFragment())
            addToBackStack(null)
            commit()
        }

        setCurrentFragment(regionsFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.RegionsFragment -> setCurrentFragment(regionsFragment)
                R.id.TeamsFragment -> setCurrentFragment(teamsFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
}