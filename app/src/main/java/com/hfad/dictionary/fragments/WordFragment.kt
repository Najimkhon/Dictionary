package com.hfad.dictionary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.dictionary.ViewModel.ViewModel
import com.hfad.dictionary.ViewModelFactory
import com.hfad.dictionary.adapters.WordAdapter
import com.hfad.dictionary.databinding.FragmentWordBinding
import com.hfad.dictionary.models.api.Definition
import com.hfad.dictionary.repository.Repository
import jp.wasabeef.recyclerview.animators.LandingAnimator



class WordFragment : Fragment() {
    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel
    private val adapter: WordAdapter by lazy { WordAdapter() }
    val args by navArgs<WordFragmentArgs>()
    var definitions = emptyList<Definition>()
    var examples = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordBinding.inflate(inflater, container, false)
        val view = binding.root

        val repository = Repository()
        val viewModelFactory = ViewModelFactory(repository, requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel::class.java)
        viewModel.getData(args.searchWord)
        viewModel.myResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                definitions = it.body()?.definitions!!
                adapter.setData(it.body()?.definitions!!)
            } else {
                Log.d("Response", it.errorBody().toString())
            }
        }
        viewModel.getExamples(args.searchWord)
        viewModel.myExamplesResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                examples = (it.body()?.examples as MutableList<String>?)!!
                adapter.setExampleData(it.body()?.examples!!)
            } else {
                Log.d("Response", it.errorBody().toString())
            }
        }
        setupRecyclerView()
        return view
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.definitionsRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.itemAnimator = LandingAnimator().apply { addDuration = 300 }
        adapter.setWordData(args.searchWord)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}