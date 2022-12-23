package com.hfad.dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.adapters.WordAdapter.*
import com.hfad.dictionary.databinding.DefinitionItemLayoutBinding
import com.hfad.dictionary.models.api.Definition
import com.hfad.dictionary.models.api.SearchResponse

class WordAdapter() : RecyclerView.Adapter<WordViewHolder>() {

    var definitionsList = emptyList<Definition>()
    var examplesList = emptyList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            DefinitionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.definitionItemLayoutBinding.tvDefinition.text = definitionsList[position].definition
        holder.definitionItemLayoutBinding.tvMeaning.text = "Meaning ${position+1}"
        holder.definitionItemLayoutBinding.tvExample.text = examplesList[position]


    }

    override fun getItemCount(): Int {
        return definitionsList.size
    }

    fun setData(defintionsList: List<Definition>){
        this.definitionsList = defintionsList
        notifyDataSetChanged()
    }
    fun setExampleData(examplesList: List<String>){
        this.examplesList = examplesList
        notifyDataSetChanged()
    }

    inner class WordViewHolder(val definitionItemLayoutBinding: DefinitionItemLayoutBinding)
        :RecyclerView.ViewHolder(definitionItemLayoutBinding.root)

}