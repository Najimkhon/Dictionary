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
import com.hfad.dictionary.repository.Repository
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class WordFragment : Fragment() {
    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel
    private val adapter: WordAdapter by lazy { WordAdapter() }
    val args by navArgs<WordFragmentArgs>()
    private val mViewModel: ViewModel by viewModels()


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
                adapter.setData(it.body()?.definitions!!)
            } else {
                Log.d("Response", it.errorBody().toString())
            }
        }
        viewModel.getExamples("example")
        viewModel.myExamplesResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}