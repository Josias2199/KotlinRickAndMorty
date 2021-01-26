package com.example.rickandmorty.ui.home

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.common.MyApp
import com.example.rickandmorty.data.local.Character
import com.example.rickandmorty.databinding.ItemContainerCharacterBinding
import com.squareup.picasso.Picasso

class HomeAdapter(private val itemClickListener: OnCharacterClickListener): RecyclerView.Adapter<HomeAdapter.MainViewHolder>() {

    private var characterList: List<Character> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemContainerCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = MainViewHolder(binding)
        binding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
            itemClickListener.onCharacterClick(characterList[position], position)
        }
        return holder
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindCharacter(characterList[position])
    }

    override fun getItemCount(): Int = characterList.size

    inner class MainViewHolder(private val binding: ItemContainerCharacterBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bindCharacter(character: Character){
            Picasso.get().load(character.image).into(binding.ivCharacter)
            validateCharacterStatus(character, binding)
            binding.txtTitle.text = character.name
            "${character.status} - ${character.species}".also { binding.txtStatusSpecie.text = it }
            binding.txtLocation.text = character.location.name
            binding.txtLastKnowLocation.text =  "Last know location"
            changeBackground(binding)
        }
    }

    fun setData(characterList: List<Character>){
        this.characterList = characterList
    }

    interface OnCharacterClickListener{
        fun onCharacterClick(character: Character, position: Int)
    }

    private fun validateCharacterStatus(character: Character, binding: ItemContainerCharacterBinding){
        when(character.status){
            "Alive" -> {binding.ivStatus.setImageResource(R.drawable.background_slider_indicator_active)}
            "unknown" -> {binding.ivStatus.setImageResource(R.drawable.background_slider_indicator_default) }
            "Dead" -> {binding.ivStatus.setImageResource(R.drawable.background_slider_indicator_inactive)}
        }
    }

    private fun changeBackground(binding: ItemContainerCharacterBinding){
        with(binding){
            linearLayoutStatus.setBackgroundResource(R.color.defaultBackground)
            txtTitle.setBackgroundResource(R.color.defaultBackground)
            txtStatusSpecie.setBackgroundResource(R.color.defaultBackground)
            txtLocation.setBackgroundResource(R.color.defaultBackground)
            txtLastKnowLocation.setBackgroundResource(R.color.defaultBackground)
        }
    }
}