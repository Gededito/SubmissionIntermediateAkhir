package com.android.dicodingstoryapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.databinding.ActivityDetailStoryBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Story Detail"

        val data = intent.getParcelableExtra<StoryResponse.StoryApp>(EXTRA_DETAIL)
        binding.tvName.text = data?.name
        binding.tvDescription.text = data?.description

        Glide.with(this)
            .load(data?.photoUrl)
            .apply(RequestOptions().override(70,70))
            .into(binding.photoDetail)

    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

}