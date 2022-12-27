package com.hfad.dictionary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hfad.dictionary.R
import com.hfad.dictionary.ViewModel.ViewModel
import com.hfad.dictionary.ViewModelFactory
import com.hfad.dictionary.databinding.FragmentAddBinding
import com.hfad.dictionary.databinding.FragmentWordBinding
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.repository.Repository

class AddFragment : Fragment() {
    val args by navArgs<AddFragmentArgs>()
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: ViewModel by viewModels() {
        ViewModelFactory(
            Repository(),
            requireActivity().application
        )
    }
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.etTitle.setText(args.currentWord.word)
        binding.etDefinition.setText(args.currentWord.definition)
        binding.etExample.setText(args.currentWord.example)
        binding.btnAddCard.setOnClickListener {
            addCardToDb()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        binding.etExample.setText(args.currentWord.example)

        binding.spStatus.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    private fun addCardToDb() {
        val mWord = binding.etTitle.text.toString()
        val mStatus = binding.spStatus.selectedItem.toString()
        val partOfSpeech = args.currentWord.partsOfSpeech
        val mDefinition = binding.etDefinition.text.toString()
        val mExample = binding.etExample.text.toString()
        val validation = mSharedViewModel.verifyDataFromUser(mWord, mDefinition)

        if (validation) {
            val newCard = Card(
                0,
                mWord,
                mSharedViewModel.parseStatus(mStatus),
                partOfSpeech,
                mDefinition,
                mExample
            )
            mViewModel.insertData(newCard)
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