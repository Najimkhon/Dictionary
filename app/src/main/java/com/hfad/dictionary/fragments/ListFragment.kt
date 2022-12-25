package com.hfad.dictionary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.dictionary.R
import com.hfad.dictionary.ViewModel.ViewModel
import com.hfad.dictionary.ViewModelFactory
import com.hfad.dictionary.adapters.ListOfCardsAdapter
import com.hfad.dictionary.adapters.WordAdapter
import com.hfad.dictionary.databinding.FragmentListBinding
import com.hfad.dictionary.databinding.FragmentWordBinding
import com.hfad.dictionary.repository.Repository
import jp.wasabeef.recyclerview.animators.LandingAnimator


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListOfCardsAdapter by lazy { ListOfCardsAdapter() }
    private val mViewModel: ViewModel by viewModels(){
        ViewModelFactory(
            Repository(),
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        mViewModel.getAllData.observe(viewLifecycleOwner){
            adapter.setCardData(it)
        }
        setupRecyclerView()

        return view
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvCardList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.itemAnimator = LandingAnimator().apply { addDuration = 300 }

    }

}