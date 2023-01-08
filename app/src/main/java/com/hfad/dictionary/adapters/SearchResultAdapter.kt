package com.hfad.dictionary.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.adapters.SearchResultAdapter.WordViewHolder
import com.hfad.dictionary.diffutils.WordDiffUtil
import com.hfad.dictionary.models.card.Card

class SearchResultAdapter(
    val context: Context,
    private val listener: SearchResultItemLayout.OnItemClickListener
) : RecyclerView.Adapter<WordViewHolder>() {

    private var wordList = mutableListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(SearchResultItemLayout(context, listener))
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = wordList[position]
        holder.layout.fillContent(currentWord, position)
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

    inner class WordViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val layout = itemView as SearchResultItemLayout
    }

}