package com.project.pokedex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.project.pokedex.R
import com.project.pokedex.data.datasources.Resource
import com.project.pokedex.data.models.PokemonEntries
import com.project.pokedex.data.models.PokemonSpecies
import com.project.pokedex.databinding.FragmentPokemonsBinding
import com.project.pokedex.ui.adapters.PokemonsAdapter
import com.project.pokedex.ui.viewmodels.RegionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.toImmutableList

@AndroidEntryPoint
class PokemonsFragment : Fragment() {
    private lateinit var binding: FragmentPokemonsBinding
    private val regionsViewModel: RegionsViewModel by viewModels()
    private lateinit var pokemonsAdapter: PokemonsAdapter
    private var pokedexName: String = ""
    var pokemonsList: List<PokemonSpecies> = listOf<PokemonSpecies>().take(5)
    var pokemons : MutableList<PokemonSpecies> = mutableListOf()
    var pokedexDescription = ""

    private val onClickHandler: (PokemonEntries) -> Unit = {
        if(pokemonsList.size < 6){
            pokemons.add(it.pokemon_species)
        } else {
            Toast.makeText(context, "Maximum items reached, create Team instead", Toast.LENGTH_SHORT).show()
        }
        if(pokemons.size > 2) binding.btCreateTeams.isEnabled = true
        pokemonsList = pokemons.toImmutableList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = this.arguments
        if (bundle != null) {
            pokedexName = bundle.getString("Pokedex",  "null")
        }
        binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regionsViewModel.getRegionPokedexData(pokedexName)

        regionsViewModel.regionPokedex.observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.regionsError.root.isVisible = false
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    pokemonsAdapter =
                        it.data?.pokemon_entries?.let { it1 ->
                            PokemonsAdapter(onClickHandler,
                                it1
                            )
                        }!!
                    binding.rvPokemons.adapter = pokemonsAdapter
                    for(i in it.data.descriptions){
                        if(i.language.name == "en"){
                            pokedexDescription = i.description
                        }
                    }
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

        binding.btCreateTeams.setOnClickListener {
            // Navigate to Create Teams fragment with the selected Pokemons
            val manager: FragmentManager = parentFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            val bundle = Bundle()
            bundle.putParcelableArray("Pokemons", pokemonsList.toTypedArray())
            bundle.putString("Pokedexdes", pokedexDescription)
            val fragment = CreateTeamsFragment()
            fragment.arguments = bundle
            transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
        }
    }
}