package com.android.dicodingstoryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.dicodingstoryapp.databinding.LoadingItemBinding


class LoadingAdapter (private val retry: () -> Unit): LoadStateAdapter<LoadingAdapter.LoadingStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadingStateViewHolder {
        val binding = LoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    class LoadingStateViewHolder(private val binding: LoadingItemBinding, retry: () -> Unit):
        RecyclerView.ViewHolder(binding.root){

            init {
                binding.btnRetry.setOnClickListener { retry.invoke() }
            }

            fun bind(loadState: LoadState) {
                if (loadState is LoadState.Error) {
                    binding.messageError.text = loadState.error.localizedMessage
                }
                binding.progressCircular.isVisible = loadState is LoadState.Loading
                binding.btnRetry.isVisible = loadState is LoadState.Error
                binding.messageError.isVisible = loadState is LoadState.Error
            }
    }

}