package com.hfad.dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.databinding.CardItemLayoutBinding
import com.hfad.dictionary.models.card.Card

class HomeAdapter(): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var mCardList = emptyList<Card>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            CardItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return mCardList.size
    }

    inner class HomeViewHolder(val cardItemLayoutBinding: CardItemLayoutBinding) :
        RecyclerView.ViewHolder(cardItemLayoutBinding.root)
}