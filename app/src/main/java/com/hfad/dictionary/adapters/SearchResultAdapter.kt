package com.hfad.dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.adapters.SearchResultAdapter.WordViewHolder
import com.hfad.dictionary.databinding.DefinitionItemLayoutBinding
import com.hfad.dictionary.diffutils.WordDiffUtil
import com.hfad.dictionary.fragments.SearchResultFragmentDirections
import com.hfad.dictionary.models.card.Card

class SearchResultAdapter : RecyclerView.Adapter<WordViewHolder>() {

    private var wordList = mutableListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            DefinitionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        val currentWord = wordList[position]

        holder.definitionItemLayoutBinding.tvDefinition.text = currentWord.definition
        holder.definitionItemLayoutBinding.tvMeaning.text = "${currentWord.word} ${position + 1}"
        holder.definitionItemLayoutBinding.tvExample.text = currentWord.example
        holder.definitionItemLayoutBinding.tvPartOfSpeech.text = currentWord.partsOfSpeech

        holder.definitionItemLayoutBinding.itemLayout.setOnClickListener {
            val action = SearchResultFragmentDirections.actionWordFragmentToAddFragment(currentWord)
            holder.definitionItemLayoutBinding.itemLayout.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    fun setData(newWordsList: List<Card>) {
        val cardDiffUtil = WordDiffUtil(wordList, newWordsList)
        val cardDiffResult = DiffUtil.calculateDiff(cardDiffUtil)
        wordList.clear()
        wordList.addAll(newWordsList)
        cardDiffResult.dispatchUpdatesTo(this)
    }

    inner class WordViewHolder(val definitionItemLayoutBinding: DefinitionItemLayoutBinding) :
        RecyclerView.ViewHolder(definitionItemLayoutBinding.root)

}