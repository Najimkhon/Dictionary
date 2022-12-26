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
import com.hfad.dictionary.databinding.FragmentUpdateBinding
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.repository.Repository


class UpdateFragment : Fragment() {
    val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val mSharedViewModel: SharedViewModel by viewModels()
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
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.etTitle.setText(args.currentWord.word)
        binding.etDefinition.setText(args.currentWord.definition)
        binding.etExample.setText(args.currentWord.example)
        binding.btnAddCard.setOnClickListener{
            updateCard()
        }
        binding.etExample.setText(args.currentWord.example)

        binding.spStatus.setSelection(mSharedViewModel.parseStatusToInt(args.currentWord.status))
        binding.spStatus.onItemSelectedListener = mSharedViewModel.listener

        return view
    }
    private fun updateCard() {
        val mWord = binding.etTitle.text.toString()
        val mStatus = mSharedViewModel.parseStatus(binding.spStatus.selectedItem.toString())
        val partOfSpeech = args.currentWord.partsOfSpeech
        val mDefinition = binding.etDefinition.text.toString()
        val mExample = binding.etExample.text.toString()
        val validation = mSharedViewModel.verifyDataFromUser(mWord, mDefinition)

        if (validation) {
            val updatedCard = Card(
                args.currentWord.id,
                mWord,
                mStatus,
                partOfSpeech,
                mDefinition,
                mExample
            )
            mViewModel.updateCard(updatedCard)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please, fill out all the fields!", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}