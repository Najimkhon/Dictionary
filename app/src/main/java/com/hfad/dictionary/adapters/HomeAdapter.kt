package com.hfad.dictionary.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.diffutils.CardDiffUtil
import com.hfad.dictionary.models.card.Card


class HomeAdapter(val context: Context, private val listener: VpItemLayout.OnVpItemClickListener) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var mCardList = emptyList<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(VpItemLayout(context, listener))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val word = mCardList[position]
        holder.layout.fillContent(word)
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

    inner class HomeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val layout = itemView as VpItemLayout
    }
}