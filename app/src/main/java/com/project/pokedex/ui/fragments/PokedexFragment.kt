package com.project.pokedex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.project.pokedex.R
import com.project.pokedex.data.datasources.Resource
import com.project.pokedex.data.models.Pokedexes
import com.project.pokedex.databinding.FragmentPokedexBinding
import com.project.pokedex.ui.adapters.SpecificRegionAdapter
import com.project.pokedex.ui.viewmodels.RegionsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokedexFragment : Fragment() {
    private lateinit var binding: FragmentPokedexBinding
    private val regionsViewModel: RegionsViewModel by viewModels()
    private lateinit var specificRegionAdapter: SpecificRegionAdapter
    private var regionName: String = ""

    private val onClickHandler: (Pokedexes) -> Unit = {
        // Navigate to Pokemons fragment with the selected Pokedex
        val manager: FragmentManager = parentFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Pokedex", it.name)
        val fragment = PokemonsFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = this.arguments
        if (bundle != null) {
            regionName = bundle.getString("Name", "null")
        }
        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regionsViewModel.navigateToRegion(regionName)

        regionsViewModel.regionData.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.regionsError.root.isVisible = false
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    specificRegionAdapter = it.data?.pokedexes?.let { it1 ->
                        SpecificRegionAdapter(
                            onClickHandler,
                            it1
                        )
                    }!!
                    binding.rvSpecificData.adapter = specificRegionAdapter
                }

                Resource.Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.regionsError.root.isVisible = true
                    binding.regionsError.btnRetry.setOnClickListener {
                        regionsViewModel.getRegions()
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }
}