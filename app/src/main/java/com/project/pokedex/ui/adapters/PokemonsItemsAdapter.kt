package com.project.pokedex.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.pokedex.R
import com.project.pokedex.data.models.PokemonSaved
import com.project.pokedex.databinding.PokemonItemBinding
import com.squareup.picasso.Picasso

class PokemonsItemsAdapter(private var pokemonList: List<PokemonSaved>) :
    RecyclerView.Adapter<PokemonsItemsAdapter.PokemonsItemsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsItemsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)
        return PokemonsItemsHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonsItemsHolder, position: Int) {
        val pokemonCard = pokemonList[position]
        holder.bind(pokemonCard)
    }

    override fun getItemCount(): Int = pokemonList.size

    inner class PokemonsItemsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val context: Context? = null
        private val binding = PokemonItemBinding.bind(view)

        fun bind(item: PokemonSaved) {
            binding.tvName.text = item.name

            // Load Pokemon image and show not supported image in case there is no image
            if (item.image != "null" && item.image.isNotEmpty()) {
                Picasso.get().load(item.image)
                    .into(binding.ivPokemon)
            } else {
                binding.ivPokemon.setImageDrawable(context?.resources?.let {
                    ResourcesCompat.getDrawable(
                        it, R.drawable.ic_baseline_image_not_supported_24, null
                    )
                })
            }
        }
    }

}