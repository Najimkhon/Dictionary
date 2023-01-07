package com.hfad.dictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hfad.dictionary.R
import com.hfad.dictionary.ViewModel.ViewModel
import com.hfad.dictionary.ViewModelFactory
import com.hfad.dictionary.adapters.HomeAdapter
import com.hfad.dictionary.databinding.FragmentHomeBinding
import com.hfad.dictionary.repository.Repository
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter: HomeAdapter by lazy { HomeAdapter() }
    private val mViewModel: ViewModel by viewModels() {
        ViewModelFactory(
            Repository(),
            requireActivity().application
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.searchView.setOnQueryTextListener(this)
        binding.tvSeeAllwords.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_wordFragment)
        }
        binding.btnSeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
        mViewModel.sortByNew.observe(viewLifecycleOwner) {
            adapter.setCardData(it)
            binding.tvLearntWords.text = it.size.toString()

        }

        setupRecyclerView()

        return view


    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvRecents
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            val action = HomeFragmentDirections.actionHomeFragmentToWordFragment(query)
            findNavController().navigate(action)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}