package com.example.sprint_marvel_serasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sprint_marvel_serasa.R
import com.example.sprint_marvel_serasa.databinding.ItemCardHeroesVerticalBinding
import com.example.sprint_marvel_serasa.model.Results

class MarvelVerticalAdapter(private val onClick: (Int) -> Unit) : ListAdapter<Results, ItemVericalViewHolder>(MarvelVerticalDiffUtils()) {

    private var listOfHeroes = mutableListOf<Results>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVericalViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_card_heroes_vertical, parent, false).apply {
            return ItemVericalViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemVericalViewHolder, position: Int) {
        getItem(position).apply {
            holder.bind(this)
            holder.itemView.setOnClickListener { onClick(this.char_id.toInt()) }
        }
    }

    fun refresh(newList : List<Results>, clear: Boolean = false) {
        if (clear) {
            listOfHeroes.clear()
        }
        listOfHeroes.addAll(newList)
        submitList(listOfHeroes.toMutableList())
    }

}
// Create a Diff Utils to refresh the list and add more components(heroes)
class MarvelVerticalDiffUtils : DiffUtil.ItemCallback<Results>() {
    override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
        return oldItem.char_id == newItem.char_id
    }

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
        return oldItem == newItem
    }
}


class ItemVericalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemCardHeroesVerticalBinding.bind(itemView)

    fun bind(result: Results) {
        binding.cvHeroVertical.setText(result.name)
        binding.cvHeroVertical.setImage(result.thumbnail.mergeImage())
//        binding.tvNameHero.text = result.name
//        binding.ivHero.apply {
//            Glide.with(itemView.context)
//                .load(result.thumbnail.mergeImage())
//                .into(this)
//        }
    }
}