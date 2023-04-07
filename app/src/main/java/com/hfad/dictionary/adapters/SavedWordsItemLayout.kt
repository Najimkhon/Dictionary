package com.hfad.dictionary.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.hfad.dictionary.R
import com.hfad.dictionary.databinding.CardItemLayoutBinding
import com.hfad.dictionary.fragments.SavedWordsFragmentDirections
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.models.card.Status

class SavedWordsItemLayout(context: Context, private val listener: OnItemClickListener) :
    RelativeLayout(context) {

    private val binding = CardItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var currentWord: Card

    init {
        binding.itemView.setOnClickListener {
            listener.onItemClicked(currentWord)
        }
    }

    fun fillContent(currentItem: Card) {
        currentWord = currentItem
        binding.tvWord.text = currentWord.word
        binding.tvDefinition.text = currentWord.definition
        binding.tvExample.text = currentWord.example
        when (currentWord.status) {
            Status.New -> binding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.red
                )
            )
            Status.Repeat -> binding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.yellow
                )
            )
            Status.Learned -> binding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.green
                )
            )
        }
        binding.itemView.setOnClickListener {
            val action =
                SavedWordsFragmentDirections.actionListFragmentToUpdateFragment(currentWord)
            binding.itemView.findNavController().navigate(action)
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(clickedItem: Card)
    }


}