package com.hfad.dictionary.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hfad.dictionary.MainViewModelFactory
import com.hfad.dictionary.R
import com.hfad.dictionary.adapters.SavedWordsAdapter
import com.hfad.dictionary.adapters.SavedWordsItemLayout
import com.hfad.dictionary.databinding.FragmentSavedWordsBinding
import com.hfad.dictionary.models.card.Card
import com.hfad.dictionary.utils.SwipeToDelete
import com.hfad.dictionary.viewmodel.MainViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class SavedWordsFragment : Fragment(), SearchView.OnQueryTextListener, SavedWordsItemLayout.OnItemClickListener {

    private var _binding: FragmentSavedWordsBinding? = null
    private val binding get() = _binding!!
    private val adapter: SavedWordsAdapter by lazy { SavedWordsAdapter(requireContext(), this) }
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedWordsBinding.inflate(inflater, container, false)
        val view = binding.root

        setObservers()

        setupRecyclerView()
        setHasOptionsMenu(true)

        return view
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvCardList
        recyclerView.adapter = adapter
        swipeToDelete(recyclerView)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }

    }

    private fun setObservers(){
        viewModel.getAllData.observe(viewLifecycleOwner) {
            adapter.setCardData(it)
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.cardList[viewHolder.adapterPosition]
                viewModel.deleteCard(itemToDelete)
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
        when (item.itemId) {
            R.id.deleteAll -> deleteAll()
            R.id.statusNew -> viewModel.sortByNew.observe(this) { adapter.setCardData(it) }
            R.id.statusLearned -> viewModel.sortByLearned.observe(this) { adapter.setCardData(it) }
            R.id.statusRepeat -> viewModel.sortByRepeat.observe(this) { adapter.setCardData(it) }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreDeletedData(view: View, deletedItem: Card, position: Int) {
        val snackbar = Snackbar.make(
            view,
            "Deleted '${deletedItem.word}'",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Undo") {
            viewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackbar.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String?) {
        val searchQuery = "%$query%"

        viewModel.searchThroughDatabase(searchQuery).observe(this) { list ->
            list?.let {
                adapter.setCardData(it)
            }
        }
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAll()
            Toast.makeText(requireContext(), "All Records are Removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All Records?")
        builder.setMessage("Are you sure you want to delete all records?")
        builder.create().show()
    }

    override fun onItemClicked(clickedItem: Card) {
        TODO("Not yet implemented")
    }
}