package com.example.sprint_marvel_serasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sprint_marvel_serasa.R
import com.example.sprint_marvel_serasa.databinding.ItemCardHeroesHorizontalBinding
import com.example.sprint_marvel_serasa.model.Results

class MarvelHorizontalAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<ItemHorizontalViewHolder>() {

    private var listOfCharacter = mutableListOf<Results>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHorizontalViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_card_heroes_horizontal, parent, false).apply {
            return ItemHorizontalViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemHorizontalViewHolder, position: Int) {
        val newPosition = position % listOfCharacter.size
        listOfCharacter[newPosition].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener { onClick(this.char_id.toInt()) }
        }
    }

    override fun getItemCount(): Int = listOfCharacter.size*1000

    fun refresh(newList: List<Results>) {
        listOfCharacter.clear()
        listOfCharacter.addAll(newList)
        notifyDataSetChanged()
    }
}

class ItemHorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding = ItemCardHeroesHorizontalBinding.bind(itemView)

    fun bind(result: Results) {
        binding.cvHeroHorizontal.setText(result.name)
        binding.cvHeroHorizontal.setImage(result.thumbnail.mergeImage())
//        binding.tvNameHero.text = result.name
//        Glide.with(itemView.context)
//            .load(result.thumbnail.mergeImage())
//            .into(binding.ivHero)
    }
}