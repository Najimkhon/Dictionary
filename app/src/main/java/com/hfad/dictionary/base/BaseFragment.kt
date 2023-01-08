package com.hfad.dictionary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: Inflate<VB>
) : Fragment() {

    protected lateinit var viewModel: VM
    protected val binding get() = _binding!!
    private var _binding: VB? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        viewModel = requireActivity().run { ViewModelProvider(this)[viewModelClass()] }
        return _binding?.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assignObjects()
        setListeners()
        prepareUI()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun viewModelClass(): Class<VM>
    protected open fun assignObjects() {}
    protected open fun setListeners() {}
    protected open fun prepareUI() {}
    protected open fun setObservers() {}

}