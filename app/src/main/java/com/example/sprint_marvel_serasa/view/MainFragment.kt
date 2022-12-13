package com.example.sprint_marvel_serasa.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sprint_marvel_serasa.R
import com.example.sprint_marvel_serasa.adapter.MarvelHorizontalAdapter
import com.example.sprint_marvel_serasa.adapter.MarvelVerticalAdapter
import com.example.sprint_marvel_serasa.databinding.MainFragmentBinding
import com.example.sprint_marvel_serasa.model.Results
import com.example.sprint_marvel_serasa.utils.checkInternetConnection
import com.example.sprint_marvel_serasa.utils.hideKeyboard
import com.example.sprint_marvel_serasa.utils.replaceView
import com.example.sprint_marvel_serasa.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private var offset = 0
    private var adapterHorizontal = MarvelHorizontalAdapter {
        requireActivity().hideKeyboard()
        requireActivity().replaceView(
            fragment = DetailFragment.newInstance(it),
            hideFragment = this,
            addToPile = true
        )
    }
    private var adapterVertical = MarvelVerticalAdapter {
        requireActivity().hideKeyboard()
        requireActivity().replaceView(
            DetailFragment.newInstance(it),
            hideFragment = this,
            addToPile = true
        )
    }

    private val observerCharacterVertical = Observer<List<Results>> {
        if (!binding.cvSearch.setNullableInput()) {
            adapterVertical.refresh(it, true)
        } else {
            adapterVertical.refresh(it)
        }
        if (!it.isNullOrEmpty()) {
            binding.incNotFoundHreoesVertical.tvNotFoundHeroes.visibility = INVISIBLE
        } else {
            binding.incNotFoundHreoesVertical.tvNotFoundHeroes.visibility = VISIBLE
        }
        binding.fabGoToTop.visibility = VISIBLE
        setupHomeLayout(VISIBLE)
        setupSplash(INVISIBLE, INVISIBLE)
    }
    private val observerCharacterHorizontal = Observer<List<Results>> {
        adapterHorizontal.refresh(it)
        if (!it.isNullOrEmpty()){
            binding.incNotFoundHreoesHorizontal.tvNotFoundHeroes.visibility = INVISIBLE
        }else {
            binding.incNotFoundHreoesHorizontal.tvNotFoundHeroes.visibility = VISIBLE
        }
    }
    private val observerLoadMoreCharacter = Observer<List<Results>> {
        binding.incVerticalList.pbLoadMoreHeroes.visibility = GONE
        adapterVertical.refresh(it)
    }
    private val observerCharacterVerticalSearchable = Observer<List<Results>> {
        adapterVertical.refresh(it, true)
        if (!it.isNullOrEmpty()) {
            binding.incNotFoundHreoesVertical.tvNotFoundHeroes.visibility = INVISIBLE
        }else {
            binding.incNotFoundHreoesVertical.tvNotFoundHeroes.visibility = VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchCharacterVertical(offset = 0, limit = 20)
        viewModel.fetchCharacterHorizontal(offset = 0, limit = 5)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadComponents(view)
        executeComponents()
    }

    private fun loadComponents(view: View) {

        binding = MainFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.characterHorizontal.observe(viewLifecycleOwner, observerCharacterHorizontal)
        viewModel.characterVertical.observe(viewLifecycleOwner, observerCharacterVertical)
        viewModel.loadMoreCharacter.observe(viewLifecycleOwner, observerLoadMoreCharacter)
        viewModel.characterVerticalSearchable.observe(
            viewLifecycleOwner,
            observerCharacterVerticalSearchable
        )
    }

    private fun executeComponents() {
        setupHomeLayout(INVISIBLE)
        setupSplash(VISIBLE, VISIBLE)
        setupRecyclerViewHorizontal()
        setupRecyclerViewVertical()
        loadMoreCharacter()
        searchHeroes()
        fabGoToTop()
    }

    private fun setupRecyclerViewHorizontal() = with(binding.incHorinzontal.rvHorinzontalList) {
        adapter = adapterHorizontal
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupRecyclerViewVertical() = with(binding.incVerticalList.rvVerticalList) {
        adapter = adapterVertical
        layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun loadMoreCharacter() = with(binding.incVerticalList.rvVerticalList) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                verifyInternetToLoadMoreCharacter(recyclerView = recyclerView, newState = newState)
            }
        })
    }

    private fun verifyEtSearchIsNull() {
        if (binding.cvSearch.setNullableInput()) {
            binding.cvSearch.getEditTextInput().let {
                viewModel.fetchLoadMoreCharacter(
                    offset = offset,
                    limit = 20,
                    nameStartWith = it.text.toString()
                )
            }
        } else {
            viewModel.fetchLoadMoreCharacter(
                offset = offset,
                limit = 20,
                nameStartWith = null
            )
        }
    }

    private fun verifyInternetToLoadMoreCharacter(recyclerView: RecyclerView, newState: Int) {
        if (checkInternetConnection(context)) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(
                    1
                )
            ) {
                binding.incVerticalList.pbLoadMoreHeroes.visibility = VISIBLE
                offset += 20
                verifyEtSearchIsNull()
            }
        } else {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1))
                Snackbar.make(requireView(),"Don't have connection to load more", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupHomeLayout(visibility: Int) {
        binding.appbarLayout.visibility = visibility
        binding.incVerticalList.rvVerticalList.visibility = visibility
    }

    private fun setupSplash(visibilitySplash: Int, visibilityLoader: Int) {
        binding.clSplashView.visibility = visibilitySplash
        binding.pbLoadMoreHeroesSplash.visibility = visibilityLoader
    }

    private fun searchHeroes() {
        binding.cvSearch.getEditTextInput().addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                text?.let {
                    if (it.isEmpty()) {
                        offset = 0
                        viewModel.fetchCharacterVerticalSearchable(
                            offset = offset,
                            limit = 20,
                            nameStartWith = null
                        )
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.cvSearch.getEditTextInput().setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                offset = 0
                viewModel.fetchCharacterVerticalSearchable(
                    offset = offset,
                    limit = 20,
                    nameStartWith = v.text.toString()
                )
                requireActivity().hideKeyboard()
                binding.cvSearch.getEditTextInput().clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun fabGoToTop() {
        binding.fabGoToTop.setOnClickListener {
            binding.incVerticalList.rvVerticalList.scrollToPosition(0)
            binding.appbarLayout.setExpanded(true)
        }
    }
}