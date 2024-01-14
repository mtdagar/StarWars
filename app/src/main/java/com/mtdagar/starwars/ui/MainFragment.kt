package com.mtdagar.starwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.GridLayoutManager
import com.mtdagar.starwars.R
import com.mtdagar.starwars.adapter.CharactersAdapter
import com.mtdagar.starwars.data.local.models.FilterOptions
import com.mtdagar.starwars.data.local.models.SortingOptions
import com.mtdagar.starwars.databinding.FragmentMainBinding
import com.mtdagar.starwars.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    private val charactersAdapter: CharactersAdapter by lazy {
        CharactersAdapter { character ->
            val action =
                MainFragmentDirections.actionMainFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
            //binding.searchView.editText!!.setText("")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver("")
        setUpAdapter()
    }

    private fun setUpObserver(searchString: String) {
        val scope = viewLifecycleOwner.lifecycleScope


        scope.launch {
            viewModel.getCharacters(searchString, null).collect {
                charactersAdapter.submitData(lifecycle, it)
            }
        }

        binding.btnFilter.setOnClickListener {
            val modal = BottomSheetDialog() {filterOptions ->
                scope.launch {
                    viewModel.getCharacters(searchString, filterOptions.sortingOption).collect {
                        charactersAdapter.submitData(lifecycle, it)
                    }
                }
            }
            parentFragmentManager.let { modal.show(it, BottomSheetDialog.TAG) }

        }
    }

    private fun setUpAdapter() {

        binding.charactersRecyclerview.apply {
            adapter = charactersAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        charactersAdapter.addLoadStateListener { loadState ->
            if (loadState.append is LoadState.Loading) {
                binding.appendProgressBar.isVisible = true
            } else {
                binding.appendProgressBar.isVisible = false
            }

            if (loadState.refresh is LoadState.Loading) {
                if (charactersAdapter.snapshot().isEmpty()) {
                    binding.charactersProgressBar.isVisible = true
                }
                binding.textViewError.isVisible = false

            } else {
                binding.charactersProgressBar.isVisible = false

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (charactersAdapter.snapshot().isEmpty()) {
                        binding.textViewError.isVisible = true
                        binding.textViewError.text = "Failed to connect to server"
                    }
                }
            }
        }
    }

}