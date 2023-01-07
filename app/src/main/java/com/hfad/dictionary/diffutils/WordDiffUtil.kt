package com.hfad.dictionary.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.hfad.dictionary.models.card.Card

class WordDiffUtil(
    private val oldList: List<Card>,
    private val newList: List<Card>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].status == newList[newItemPosition].status
                && oldList[oldItemPosition].partsOfSpeech == newList[newItemPosition].partsOfSpeech
                && oldList[oldItemPosition].definition == newList[newItemPosition].definition
                && oldList[oldItemPosition].example == newList[newItemPosition].example

    }
}