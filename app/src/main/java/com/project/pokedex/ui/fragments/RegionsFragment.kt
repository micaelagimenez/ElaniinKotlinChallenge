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
import com.project.pokedex.data.models.Region
import com.project.pokedex.databinding.FragmentRegionsBinding
import com.project.pokedex.ui.adapters.RegionAdapter
import com.project.pokedex.ui.viewmodels.RegionsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegionsFragment : Fragment() {

    private lateinit var binding: FragmentRegionsBinding
    private lateinit var regionsAdapter: RegionAdapter
    private val regionsViewModel: RegionsViewModel by viewModels()
    private val onClickHandler: (Region) -> Unit = {
        // Open selected region's screen
        val manager: FragmentManager = parentFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Name", it.name)
        val fragment = PokedexFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegionsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRegionsList()
    }

    private fun setupRegionsList() {
        regionsViewModel.getRegions()
        regionsViewModel.regions.observe(
            viewLifecycleOwner
        ) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.regionsError.root.isVisible = false
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    binding.regionsError.root.isVisible = false
                    regionsAdapter =
                        it.data?.results?.let { it1 -> RegionAdapter(onClickHandler, it1) }!!
                    binding.rvRegions.adapter = regionsAdapter
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