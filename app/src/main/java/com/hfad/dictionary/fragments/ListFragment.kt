package com.hfad.dictionary.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hfad.dictionary.R
import com.hfad.dictionary.ViewModel.ViewModel
import com.hfad.dictionary.ViewModelFactory
import com.hfad.dictionary.adapters.ListAdapter
import com.hfad.dictionary.databinding.FragmentListBinding
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.repository.Repository
import com.hfad.dictionary.utils.SwipeToDelete
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListAdapter by lazy { ListAdapter() }
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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_listFragment_to_homeFragment)
        }
        setHasOptionsMenu(true)

        return view
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvCardList
        recyclerView.adapter = adapter
        swipeToDelete(recyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }

    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.mCardList[viewHolder.adapterPosition]
                mViewModel.deleteCard(itemToDelete)
                adapter.notifyItemChanged(viewHolder.adapterPosition)
                restoreDeletedData(viewHolder.itemView, itemToDelete, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_of_cards_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteAll -> deleteAll()
            R.id.statusNew -> mViewModel.sortByNew.observe(this) { adapter.setCardData(it) }
            R.id.statusLearned -> mViewModel.sortByLearned.observe(this) { adapter.setCardData(it) }
            R.id.statusRepeat -> mViewModel.sortByRepeat.observe(this) { adapter.setCardData(it) }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreDeletedData(view: View, deletedItem: Card, position: Int){
        val snackbar = Snackbar.make(
            view,
            "Deleted '${deletedItem.word}'",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Undo"){
            mViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackbar.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }
    private fun searchDatabase(query: String?) {
        var searchQuery = "%$query%"

        mViewModel.searchThroughDatabase(searchQuery).observe(this, Observer { list->
            list?.let {
                adapter.setCardData(it)
            }
        })
    }
    private fun deleteAll(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            mViewModel.deleteAll()
            Toast.makeText(requireContext(), "All Records are Removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete All Records?")
        builder.setMessage("Are you sure you want to delete all records?")
        builder.create().show()
    }
}