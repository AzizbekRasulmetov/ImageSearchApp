package com.example.mysearchphotosapp.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mysearchphotosapp.R
import com.example.mysearchphotosapp.adapters.UnsplashLoadStateAdapter
import com.example.mysearchphotosapp.adapters.UnsplashPhotoAdapter
import com.example.mysearchphotosapp.databinding.FragmentGalleryBinding
import com.example.mysearchphotosapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val binding: FragmentGalleryBinding by viewBinding()
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var photoAdapter: UnsplashPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setLoadStateListener()

        //Observe photos from view model
        viewModel.photos.observe(viewLifecycleOwner, { data ->
            photoAdapter.submitData(viewLifecycleOwner.lifecycle, data)
        })

        binding.buttonRetry.setOnClickListener {
            photoAdapter.retry()
        }

        photoAdapter.setPhotoClickListener {
            val bundle = Bundle().apply {
                putParcelable("photo", it)
            }
            findNavController().navigate(R.id.action_galleryFragment_to_photoFragment, bundle)
        }

        setHasOptionsMenu(true)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {

            itemAnimator = null
            setHasFixedSize(true)

            photoAdapter = UnsplashPhotoAdapter()
            adapter = photoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashLoadStateAdapter { photoAdapter.retry() },
                footer = UnsplashLoadStateAdapter { photoAdapter.retry() }
            )
        }
    }

    private fun setLoadStateListener() {
        photoAdapter.addLoadStateListener { loadState ->
            binding.apply {
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && photoAdapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        //Set up search functionality
        val searchItem = menu.findItem(R.id.seach_action)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

}