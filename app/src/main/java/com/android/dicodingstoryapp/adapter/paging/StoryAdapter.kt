package com.android.dicodingstoryapp.adapter.paging

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.databinding.ItemStoryBinding
import com.android.dicodingstoryapp.ui.detail.DetailStoryActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class StoryAdapter: PagingDataAdapter<StoryResponse.StoryApp, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        internal fun bind(story: StoryResponse.StoryApp) {
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .apply(RequestOptions().override(70, 70))
                .into(binding.photo)
            binding.tvItemName.text = story.name
            binding.tvItemDescription.text = story.description

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java).apply {
                    putExtra(DetailStoryActivity.EXTRA_DETAIL, story)
                }

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.tvItemName, "name"),
                        Pair(binding.tvItemDescription, "desc")
                    )
                it.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<StoryResponse.StoryApp>() {
            override fun areItemsTheSame(
                oldItem: StoryResponse.StoryApp,
                newItem: StoryResponse.StoryApp
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryResponse.StoryApp,
                newItem: StoryResponse.StoryApp
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}