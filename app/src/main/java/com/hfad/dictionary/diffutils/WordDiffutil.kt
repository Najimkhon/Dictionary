package com.hfad.dictionary.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.hfad.dictionary.models.api.Definition

class WordDiffutil(
    private val oldList: List<Definition>,
    private val newList: List<Definition>
): DiffUtil.Callback() {
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
        return oldList[oldItemPosition].definition == newList[newItemPosition].definition
                && oldList[oldItemPosition].partOfSpeech == newList[newItemPosition].partOfSpeech

    }
}