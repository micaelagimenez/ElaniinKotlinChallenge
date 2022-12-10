package com.project.pokedex.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.pokedex.R
import com.project.pokedex.data.models.Region
import com.project.pokedex.databinding.RegionItemBinding

class RegionAdapter(val onClickHandler: (Region) -> Unit, private var regionsList: List<Region>) :
    RecyclerView.Adapter<RegionAdapter.RegionsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.region_item, parent, false)
        return RegionsHolder(view)
    }

    override fun onBindViewHolder(holder: RegionsHolder, position: Int) {
        val regionCard = regionsList[position]
        holder.bind(regionCard)

        holder.regionName.setOnClickListener {
            onClickHandler(regionsList[position])
        }
    }

    override fun getItemCount(): Int = regionsList.size

    inner class RegionsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RegionItemBinding.bind(view)
        val regionName: TextView = binding.tvRegionName

        fun bind(regions: Region) {
            regionName.text = regions.name
        }
}

}