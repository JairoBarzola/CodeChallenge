package com.jairbarzola.zoluxionescodechallenge.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.jairbarzola.zoluxionescodechallenge.R
import com.jairbarzola.zoluxionescodechallenge.domain.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        val MOVIE_EXTRA = "movie"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupUI(Gson().fromJson(intent.getStringExtra(MOVIE_EXTRA),Movie::class.java))
    }

    private fun setupUI(movie: Movie) {
        detailTitleTextview.text = movie.title
        detailOverviewTextview.text = movie.overview
        detailReleaseTextview.text = String.format(getString(R.string.release_movie),movie.releaseDate)
        detailAverageTextview.text = movie.voteAverage.toString()

        Glide.with(this)
            .load(movie.poster)
            .transition(GenericTransitionOptions.with(R.anim.fab_in))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(detailPosterImageview)

        detailBackImageview.setOnClickListener { finish() }
    }
}