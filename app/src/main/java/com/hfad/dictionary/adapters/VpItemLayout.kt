package com.hfad.dictionary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.hfad.dictionary.R
import com.hfad.dictionary.databinding.VpItemHolderBinding
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.models.card.Status

class VpItemLayout(context: Context, private val listener: OnVpItemClickListener) :
    ConstraintLayout(context) {

    private val binding = VpItemHolderBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var currentWord: Card

    init {
        layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        binding.itemView.setOnClickListener {
            listener.onVpItemClickListener(currentWord)
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

    }

    interface OnVpItemClickListener {
        fun onVpItemClickListener(clickedItem: Card)
    }
}