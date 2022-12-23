package com.hfad.dictionary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hfad.dictionary.R
import com.hfad.dictionary.ViewModel.ViewModel
import com.hfad.dictionary.ViewModelFactory
import com.hfad.dictionary.databinding.FragmentHomeBinding
import com.hfad.dictionary.repository.Repository


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.imageView.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_wordFragment)
        }


        return view



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}