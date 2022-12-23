package com.hfad.dictionary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hfad.dictionary.R
import com.hfad.dictionary.ViewModel.ViewModel
import com.hfad.dictionary.ViewModelFactory
import com.hfad.dictionary.databinding.FragmentWordBinding
import com.hfad.dictionary.repository.Repository


class WordFragment : Fragment() {
    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel
    private var definitionsList : ArrayList<String> = arrayListOf()
    private var examplesList : ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordBinding.inflate(inflater, container, false)
        val view = binding.root

        val repository = Repository()
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel::class.java)
        viewModel.getData("example")
        viewModel.myResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful){
                Log.d("Response", it.body()?.id!!)
                binding.tvWord.text = it.body()?.id!!
                it.body()?.results?.forEach{
                    it.lexicalEntries.forEach{
                        it.entries.forEach{
                            it.senses.forEach{
                                it.definitions.forEach{
                                    definitionsList.add(it)
                                }
                            }
                        }
                    }
                }
                it.body()?.results?.forEach{
                    it.lexicalEntries.forEach{
                        it.entries.forEach{
                            it.senses.forEach{
                                it.examples.forEach{
                                    examplesList.add(it.text)
                                }
                            }
                        }
                    }
                }
                binding.tvExample.text = examplesList[0]
                binding.tvDefinition.text = definitionsList[0]
            }else{
                Log.d("Response", it.errorBody().toString())
            }
        }





        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}