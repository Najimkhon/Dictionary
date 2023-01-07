package com.hfad.dictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hfad.dictionary.MainViewModelFactory
import com.hfad.dictionary.R
import com.hfad.dictionary.databinding.FragmentAddBinding
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.viewmodel.MainViewModel
import com.hfad.dictionary.viewmodel.SharedViewModel

class AddFragment : Fragment() {

    val args by navArgs<AddFragmentArgs>()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.etTitle.setText(args.currentWord.word)
        binding.etDefinition.setText(args.currentWord.definition)
        binding.etExample.setText(args.currentWord.example)

        binding.btnAddCard.setOnClickListener {
            addCardToDb()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        binding.spStatus.onItemSelectedListener = sharedViewModel.listener

        return view
    }

    private fun addCardToDb() {
        val word = binding.etTitle.text.toString()
        val status = binding.spStatus.selectedItem.toString()
        val partOfSpeech = args.currentWord.partsOfSpeech
        val definition = binding.etDefinition.text.toString()
        val example = binding.etExample.text.toString()
        val validation = sharedViewModel.verifyDataFromUser(word, definition)

        if (validation) {
            val newCard = Card(
                0,
                word,
                sharedViewModel.parseStatus(status),
                partOfSpeech,
                definition,
                example
            )
            mainViewModel.insertData(newCard)
        } else {
            Toast.makeText(requireContext(), "Please, fill out all the fields!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}