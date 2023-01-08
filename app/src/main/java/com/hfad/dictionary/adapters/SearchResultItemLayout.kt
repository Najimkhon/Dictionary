package com.hfad.dictionary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.hfad.dictionary.databinding.DefinitionItemLayoutBinding
import com.hfad.dictionary.fragments.SearchResultFragmentDirections
import com.hfad.dictionary.models.card.Card


class SearchResultItemLayout(context: Context, private val listener: OnItemClickListener) :
    RelativeLayout(context) {
    private val binding =
        DefinitionItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var currentWord: Card

    init {
        binding.itemLayout.setOnClickListener {
            listener.onItemClicked(currentWord)
        }
    }

    fun fillContent(currentItem: Card, position: Int) {
        currentWord = currentItem
        binding.tvDefinition.text = currentWord.definition
        binding.tvMeaning.text = "${currentWord.word} ${position + 1}"
        binding.tvExample.text = currentWord.example
        binding.tvPartOfSpeech.text = currentWord.partsOfSpeech

        binding.itemLayout.setOnClickListener {
            val action = SearchResultFragmentDirections.actionWordFragmentToAddFragment(currentWord)
            binding.itemLayout.findNavController().navigate(action)
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(clickedItem: Card)
    }


}