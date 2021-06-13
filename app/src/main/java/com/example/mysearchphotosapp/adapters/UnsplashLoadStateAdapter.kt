package com.example.mysearchphotosapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysearchphotosapp.databinding.LoadLayoutBinding

class UnsplashLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnsplashLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: LoadLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun onBind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState !is LoadState.Loading
                loadingErrorTxt.isVisible = loadState !is LoadState.Loading
            }
        }

    }


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LoadLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


}