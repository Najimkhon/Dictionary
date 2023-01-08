package com.hfad.dictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.dictionary.MainViewModelFactory
import com.hfad.dictionary.R
import com.hfad.dictionary.adapters.HomeAdapter
import com.hfad.dictionary.adapters.VpItemLayout
import com.hfad.dictionary.databinding.FragmentHomeBinding
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.viewmodel.MainViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class HomeFragment : Fragment(), SearchView.OnQueryTextListener, VpItemLayout.OnVpItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter: HomeAdapter by lazy { HomeAdapter(requireContext(), this) }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
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

        binding.tvSeeAllWords.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
        binding.btnSeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
        viewModel.sortByNew.observe(viewLifecycleOwner) {
            adapter.setCardData(it)
            binding.tvLearntWords.text = it.size.toString()
        }



        setupRecyclerView()

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

    private fun setupRecyclerView() {
        val viewPager = binding.vpRecents
        viewPager.adapter = adapter
    }

    override fun onVpItemClickListener(clickedItem: Card) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToUpdateFragment(clickedItem)
        findNavController().navigate(action)
    }
}