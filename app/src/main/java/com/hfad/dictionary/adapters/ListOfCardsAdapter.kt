package com.hfad.dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.databinding.CardItemLayoutBinding
import com.hfad.dictionary.databinding.DefinitionItemLayoutBinding
import com.hfad.dictionary.diffutils.CardDiffUtil
import com.hfad.dictionary.models.card.Card


open class ListOfCardsAdapter():RecyclerView.Adapter<ListOfCardsAdapter.ListViewHolder>() {

    var mCardList = emptyList<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            CardItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.cardItemLayoutBinding.tvWord.text = mCardList[position].word
        holder.cardItemLayoutBinding.tvDefinition.text = mCardList[position].definition
        holder.cardItemLayoutBinding.tvExample.text = mCardList[position].example
    }

    override fun getItemCount(): Int {
        return mCardList.size
    }

    fun setCardData(cardList: List<Card>) {
        val cardDiffUtil = CardDiffUtil(mCardList, cardList)
        val cardDiffResult = DiffUtil.calculateDiff(cardDiffUtil)
        this.mCardList = cardList
        cardDiffResult.dispatchUpdatesTo(this)
    }



    inner class ListViewHolder(val cardItemLayoutBinding: CardItemLayoutBinding) :
        RecyclerView.ViewHolder(cardItemLayoutBinding.root)


}