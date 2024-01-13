package com.mtdagar.starwars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtdagar.starwars.data.local.models.CharacterEntity
import com.mtdagar.starwars.databinding.CharactersRowBinding

class CharactersAdapter(private val onClick : (character: CharacterEntity) -> Unit) :
    PagingDataAdapter<CharacterEntity, CharactersAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    inner class MyViewHolder(private val binding: CharactersRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterEntity?) {
            binding.nameTextView.text = character?.name
            binding.dobTextView.text = character?.birthYear
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CharactersRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)

        holder.itemView.setOnClickListener {
            //onClickListener.onClick(character!!)
            character?.run(onClick)
        }
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}