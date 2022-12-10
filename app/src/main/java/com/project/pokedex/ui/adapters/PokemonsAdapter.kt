package com.project.pokedex.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.pokedex.R
import com.project.pokedex.data.models.PokemonEntries
import com.project.pokedex.databinding.PokemonChooseItemBinding

class PokemonsAdapter(val onClickHandler: (PokemonEntries) -> Unit, private var pokedexesList: List<PokemonEntries>) :
    RecyclerView.Adapter<PokemonsAdapter.PokemonsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_choose_item, parent, false)
        return PokemonsHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonsHolder, position: Int) {
        val dataCard = pokedexesList[position]
        holder.bind(dataCard)

        holder.checkbox.setOnClickListener {
            onClickHandler(pokedexesList[position])
        }
    }
    override fun getItemCount(): Int = pokedexesList.size

    inner class PokemonsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PokemonChooseItemBinding.bind(view)
        val pokemonName: TextView = binding.tvName
        val checkbox: Button = binding.cbItem

        fun bind(pokedexes: PokemonEntries) {
            pokemonName.text = pokedexes.pokemon_species.name
        }
    }

}