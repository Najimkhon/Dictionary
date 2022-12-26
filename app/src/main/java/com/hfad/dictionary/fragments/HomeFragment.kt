package com.hfad.dictionary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.navigation.fragment.findNavController
import com.hfad.dictionary.R
import com.hfad.dictionary.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.searchView.setOnQueryTextListener(this)


        binding.tvSeeAllwords.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_wordFragment)
        }
        binding.btnSeeAll.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }



        return view


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