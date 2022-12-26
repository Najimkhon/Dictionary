package com.hfad.dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.adapters.WordAdapter.*
import com.hfad.dictionary.databinding.DefinitionItemLayoutBinding
import com.hfad.dictionary.fragments.WordFragmentDirections
import com.hfad.dictionary.models.api.Definition
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.models.card.Status


class WordAdapter() : RecyclerView.Adapter<WordViewHolder>() {

    var definitionsList = emptyList<Definition>()
    var examplesList = emptyList<String>()
    var word = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            DefinitionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val cardList = makeCards()
        holder.definitionItemLayoutBinding.tvDefinition.text = definitionsList[position].definition
        holder.definitionItemLayoutBinding.tvMeaning.text = "Meaning ${position + 1}"
        holder.definitionItemLayoutBinding.tvExample.text = cardList[position].example
        holder.definitionItemLayoutBinding.itemLayout.setOnClickListener {
            val action = WordFragmentDirections.actionWordFragmentToAddFragment(cardList[position])
            holder.definitionItemLayoutBinding.itemLayout.findNavController()
                .navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return definitionsList.size
    }

    fun setData(defintionsList: List<Definition>) {
        this.definitionsList = defintionsList
        notifyDataSetChanged()
    }

    fun setExampleData(examplesList: List<String>) {
        this.examplesList = examplesList
        notifyDataSetChanged()
    }

    fun setWordData(word: String) {
        this.word = word
        notifyDataSetChanged()
    }

    fun makeCards(): ArrayList<Card> {
        val cardList = arrayListOf<Card>()
        for (i in 0 until definitionsList.size) {
            if (i < examplesList.size) {
                cardList.add(
                    Card(
                        0,
                        word,
                        Status.New,
                        definitionsList[i].partOfSpeech,
                        definitionsList[i].definition,
                        examplesList[i]
                    )
                )
            } else {
                cardList.add(
                    Card(
                        0,
                        word,
                        Status.New,
                        definitionsList[i].partOfSpeech,
                        definitionsList[i].definition,
                        "No example available. Please, write your own!"
                    )
                )
            }
        }

        return cardList
    }


    inner class WordViewHolder(val definitionItemLayoutBinding: DefinitionItemLayoutBinding) :
        RecyclerView.ViewHolder(definitionItemLayoutBinding.root)

}