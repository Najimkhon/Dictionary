package com.hfad.dictionary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.hfad.dictionary.R
import com.hfad.dictionary.databinding.FragmentAddBinding
import com.hfad.dictionary.databinding.FragmentWordBinding

class AddFragment : Fragment() {
    val args by navArgs<AddFragmentArgs>()
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.etTitle.setText(args.currentWord.word)
        binding.etDefinition.setText(args.currentWord.definition)
        binding.etExample.setText(args.currentWord.example)


        return view
    }

}