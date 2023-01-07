package com.hfad.dictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.dictionary.MainViewModelFactory
import com.hfad.dictionary.adapters.SearchResultAdapter
import com.hfad.dictionary.databinding.FragmentSearchResultBinding
import com.hfad.dictionary.viewmodel.MainViewModel
import jp.wasabeef.recyclerview.animators.LandingAnimator

class SearchResultFragment : Fragment() {

    val args by navArgs<SearchResultFragmentArgs>()

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private val adapter: SearchResultAdapter by lazy { SearchResultAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        val view = binding.root

        val mainViewModelFactory = MainViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        setupRecyclerView()

        viewModel.wordResultListLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                MainViewModel.UiState.Loading -> {
                    binding.rvWords.isGone = true
                    binding.tvMessage.isGone = true
                    binding.pbLoading.isVisible = true
                }
                MainViewModel.UiState.NetworkError -> {
                    binding.rvWords.isGone = true
                    binding.pbLoading.isGone = true
                    binding.tvMessage.isVisible = true
                    binding.tvMessage.text = "Please, check your internet connection!"
                }
                MainViewModel.UiState.EmptyResult -> {
                    binding.rvWords.isGone = true
                    binding.pbLoading.isGone = true
                    binding.tvMessage.isVisible = true
                    binding.tvMessage.text = "We couldn't find any results"
                }
                MainViewModel.UiState.Success -> {
                    binding.tvMessage.isGone = true
                    binding.pbLoading.isGone = true
                    binding.rvWords.isVisible = true
                }
            }
        }
        viewModel.searchForWord(args.searchWord)
        return view
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvWords
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.itemAnimator = LandingAnimator().apply { addDuration = 300 }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}