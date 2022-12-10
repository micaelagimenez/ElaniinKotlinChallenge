package com.project.pokedex.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.project.pokedex.R
import com.project.pokedex.data.models.Team
import com.project.pokedex.databinding.TeamsItemBinding

class TeamsAdapter(val onClickHandler: (String?) -> Unit,
                   options: FirebaseRecyclerOptions<Team>
) : FirebaseRecyclerAdapter<Team, TeamsAdapter.TeamsHolder?>(options) {
    private var expandedPosition = -1
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsHolder =
        TeamsHolder(
            TeamsItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.teams_item,
                    parent,
                    false
                )
            )
        )

    inner class TeamsHolder(binding: TeamsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val teamName: TextView = binding.tvTeamName
        val teamDescription: TextView = binding.tvDescription
        val deleteButton: ImageButton = binding.btDelete
        val modifyButton: ImageButton = binding.btModify
        var pokemonRecyclerView = binding.rvPokemonsItems
        val arrowButton = binding.arrowButton
        val hiddenView = binding.hiddenView
    }

    override fun onBindViewHolder(holder: TeamsHolder, position: Int, model: Team) {
        val context: Context? = null
        val isExpanded = position == expandedPosition
        holder.hiddenView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isExpanded

        holder.teamName.text = model.name
        if(model.pokedexDescription == "null"){
            holder.teamDescription.text = context?.resources?.getString(R.string.teams_adapter_no_description_msg)
        } else {
            holder.teamDescription.text = model.pokedexDescription
        }

        holder.deleteButton.setOnClickListener {
            val item: DatabaseReference = getRef(position)
            item.removeValue()
        }

        holder.modifyButton.setOnClickListener {
            val item: DatabaseReference = getRef(position)
            onClickHandler(item.key)
        }

        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) -1 else position
            if (isExpanded) {
                holder.arrowButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            } else {
                holder.arrowButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
            notifyItemChanged(position, true)
        }

        val pokemonAdapter = PokemonsItemsAdapter(model.pokemons)
        holder.pokemonRecyclerView.adapter = pokemonAdapter
        holder.pokemonRecyclerView.setRecycledViewPool(viewPool)
    }
}