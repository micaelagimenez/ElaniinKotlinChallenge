package com.project.pokedex.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.pokedex.R
import com.project.pokedex.data.models.Pokedexes
import com.project.pokedex.databinding.PokedexItemBinding

class SpecificRegionAdapter(val onClickHandler: (Pokedexes) -> Unit, private var pokedexesList: List<Pokedexes>) :
    RecyclerView.Adapter<SpecificRegionAdapter.SpecificRegionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificRegionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokedex_item, parent, false)
        return SpecificRegionHolder(view)
    }

    override fun onBindViewHolder(holder: SpecificRegionHolder, position: Int) {
        val dataCard = pokedexesList[position]
        holder.bind(dataCard)

        holder.chooseButton.setOnClickListener {
            onClickHandler(pokedexesList[position])
        }
    }
    override fun getItemCount(): Int = pokedexesList.size

    inner class SpecificRegionHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PokedexItemBinding.bind(view)
        val pokemonName: TextView = binding.tvName
        val chooseButton: Button = binding.btItem

        fun bind(pokedexes: Pokedexes) {
            pokemonName.text = pokedexes.name
        }
    }

}