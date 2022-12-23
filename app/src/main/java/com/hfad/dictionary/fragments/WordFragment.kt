package com.hfad.dictionary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hfad.dictionary.ViewModel.ViewModel
import com.hfad.dictionary.ViewModelFactory
import com.hfad.dictionary.databinding.FragmentWordBinding
import com.hfad.dictionary.repository.Repository


class WordFragment : Fragment() {
    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel


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
            if (it.isSuccessful) {
               binding.tvWord.text = it.body()?.word.toString()
                var definitons = ""
                it.body()?.definitions?.forEach {
                    definitons += it.definition
                }
                binding.tvDefinition.text = definitons
            } else {
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