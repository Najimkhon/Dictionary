package com.hfad.dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.R
import com.hfad.dictionary.databinding.CardItemLayoutBinding
import com.hfad.dictionary.diffutils.CardDiffUtil
import com.hfad.dictionary.fragments.SavedWordsFragmentDirections
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.models.card.Status

open class SavedWordsAdapter : RecyclerView.Adapter<SavedWordsAdapter.ListViewHolder>() {

    var cardList = emptyList<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            CardItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val card = cardList[position]
        holder.cardItemLayoutBinding.tvWord.text = card.word
        holder.cardItemLayoutBinding.tvDefinition.text = card.definition
        holder.cardItemLayoutBinding.tvExample.text = card.example
        when (card.status) {
            Status.New -> holder.cardItemLayoutBinding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardItemLayoutBinding.root.context,
                    R.color.red
                )
            )
            Status.Repeat -> holder.cardItemLayoutBinding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardItemLayoutBinding.root.context,
                    R.color.yellow
                )
            )
            Status.Learned -> holder.cardItemLayoutBinding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardItemLayoutBinding.root.context,
                    R.color.green
                )
            )
        }
        holder.cardItemLayoutBinding.itemView.setOnClickListener {
            val action =
                SavedWordsFragmentDirections.actionListFragmentToUpdateFragment(card)
            holder.cardItemLayoutBinding.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun setCardData(cardList: List<Card>) {
        val cardDiffUtil = CardDiffUtil(this.cardList, cardList)
        val cardDiffResult = DiffUtil.calculateDiff(cardDiffUtil)
        this.cardList = cardList
        cardDiffResult.dispatchUpdatesTo(this)
    }

    inner class ListViewHolder(val cardItemLayoutBinding: CardItemLayoutBinding) :
        RecyclerView.ViewHolder(cardItemLayoutBinding.root)

}