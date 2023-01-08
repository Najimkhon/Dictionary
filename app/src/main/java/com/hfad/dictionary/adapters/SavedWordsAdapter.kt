package com.hfad.dictionary.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.diffutils.CardDiffUtil
import com.hfad.dictionary.models.card.Card

open class SavedWordsAdapter(val context: Context, private val listener: SavedWordsItemLayout.OnItemClickListener) : RecyclerView.Adapter<SavedWordsAdapter.SavedWordsViewHolder>() {

    var cardList = emptyList<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedWordsViewHolder {
        return SavedWordsViewHolder(SavedWordsItemLayout(context, listener))
    }

    override fun onBindViewHolder(holder: SavedWordsViewHolder, position: Int) {
        val card = cardList[position]
        holder.layout.fillContent(card)
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

    inner class SavedWordsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView){
        val layout = itemView as SavedWordsItemLayout
        }
}