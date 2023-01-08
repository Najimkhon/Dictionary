package com.hfad.dictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hfad.dictionary.MainViewModelFactory
import com.hfad.dictionary.databinding.FragmentUpdateBinding
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.viewmodel.MainViewModel
import com.hfad.dictionary.viewmodel.SharedViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.etTitle.setText(args.currentWord.word)
        binding.etDefinition.setText(args.currentWord.definition)
        binding.etExample.setText(args.currentWord.example)
        binding.btnAddCard.setOnClickListener {
            updateCard()
        }
        binding.etExample.setText(args.currentWord.example)

        binding.spStatus.setSelection(sharedViewModel.parseStatusToInt(args.currentWord.status))
        binding.spStatus.onItemSelectedListener = sharedViewModel.listener

        return view
    }

    private fun updateCard() {
        val mWord = binding.etTitle.text.toString()
        val mStatus = sharedViewModel.parseStatus(binding.spStatus.selectedItem.toString())
        val partOfSpeech = args.currentWord.partsOfSpeech
        val mDefinition = binding.etDefinition.text.toString()
        val mExample = binding.etExample.text.toString()
        val validation = sharedViewModel.verifyDataFromUser(mWord, mDefinition)

        if (validation) {
            val updatedCard = Card(
                args.currentWord.id,
                mWord,
                mStatus,
                partOfSpeech,
                mDefinition,
                mExample
            )
            viewModel.updateCard(updatedCard)
            findNavController().navigateUp()
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