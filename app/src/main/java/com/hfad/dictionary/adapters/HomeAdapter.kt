package com.hfad.dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.R
import com.hfad.dictionary.databinding.CardItemLayoutBinding
import com.hfad.dictionary.databinding.VpItemHolderBinding
import com.hfad.dictionary.diffutils.CardDiffUtil
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.models.card.Status

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var mCardList = emptyList<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            VpItemHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.vpItemHolderBinding.tvWord.text = mCardList[position].word
        holder.vpItemHolderBinding.tvDefinition.text = mCardList[position].definition
        holder.vpItemHolderBinding.tvExample.text = mCardList[position].example
        when (mCardList[position].status) {
            Status.New -> holder.vpItemHolderBinding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.vpItemHolderBinding.root.context,
                    R.color.red
                )
            )
            Status.Repeat -> holder.vpItemHolderBinding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.vpItemHolderBinding.root.context,
                    R.color.yellow
                )
            )
            Status.Learned -> holder.vpItemHolderBinding.cvStatus.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.vpItemHolderBinding.root.context,
                    R.color.green
                )
            )
        }
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

    inner class HomeViewHolder(val vpItemHolderBinding: VpItemHolderBinding) :
        RecyclerView.ViewHolder(vpItemHolderBinding.root)
}