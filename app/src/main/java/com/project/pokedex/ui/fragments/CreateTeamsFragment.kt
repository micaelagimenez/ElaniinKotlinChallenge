package com.project.pokedex.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.google.firebase.database.FirebaseDatabase
import com.project.pokedex.R
import com.project.pokedex.data.models.PokemonSaved
import com.project.pokedex.data.models.PokemonSpecies
import com.project.pokedex.data.models.Team
import com.project.pokedex.databinding.FragmentCreateTeamsBinding
import com.project.pokedex.ui.viewmodels.RegionsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateTeamsFragment : Fragment() {

    private lateinit var binding: FragmentCreateTeamsBinding
    var firebaseDatabase: FirebaseDatabase? = null
    lateinit var pokemonsList: Array<PokemonSpecies>
    lateinit var pokedexDescription: String
    private val regionsViewModel: RegionsViewModel by viewModels()
    private var pokemonImage = ""
    private var pokemonName = ""
    private var pokemonSavedList: MutableList<PokemonSaved> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTeamsBinding.inflate(inflater, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()

        val bundle = this.arguments
        if (bundle != null) {
            pokemonsList = bundle.getParcelableArray("Pokemons") as Array<PokemonSpecies>
            pokedexDescription = bundle.getString("Pokedexdes", "null")
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // textWatcher to enable Create Team button
        val watcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val nameNotEmpty: Boolean = binding.etName.text?.isNotEmpty() == true
                val numberNotEmpty: Boolean = binding.etType.text?.isNotEmpty() == true
                val typeNotEmpty: Boolean = binding.etType.text?.isNotEmpty() == true
                binding.btCreateTeams.isEnabled = nameNotEmpty && numberNotEmpty && typeNotEmpty
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        }

        binding.tfName.editText?.addTextChangedListener(watcher)
        binding.tfNumber.editText?.addTextChangedListener(watcher)
        binding.tfType.editText?.addTextChangedListener(watcher)

        for(i in pokemonsList){
            regionsViewModel.getRegionPokemonData(i.name)
        }

        regionsViewModel.regionPokemon.observe(viewLifecycleOwner){
            if(it.data?.name != null ) pokemonName = it.data.name
            if(it.data?.sprites?.back_default != null ) pokemonImage = it.data.sprites.back_default
            if(pokemonName != "" && pokemonImage != "") pokemonSavedList.add(PokemonSaved(pokemonName, pokemonImage))
        }

        binding.btCreateTeams.setOnClickListener {
            addDataToFirebase(
                binding.etName.text.toString(),
                binding.etNumber.text.toString(),
                binding.etType.text.toString(),
                pokedexDescription,
                pokemonSavedList
            )
        }

    }

    private fun addDataToFirebase(name: String, number: String, type: String, pokedexDescription: String, pokedexItem: MutableList<PokemonSaved>) {
        val team = Team()
        team.name = name
        team.number = number
        team.type = type
        team.pokedexDescription = pokedexDescription
        team.pokemons = pokedexItem

        val rootRef = FirebaseDatabase.getInstance().reference
        val tasksRef = rootRef.child("Team").push()
        tasksRef.setValue(team)
        Toast.makeText(context, "New team created", Toast.LENGTH_SHORT).show()

        // Navigate to Teams fragment after creating new team
        val manager: FragmentManager = parentFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, TeamsFragment()).addToBackStack(null).commit()
    }
}
