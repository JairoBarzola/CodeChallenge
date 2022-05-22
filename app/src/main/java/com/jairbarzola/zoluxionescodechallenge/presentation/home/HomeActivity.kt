package com.jairbarzola.zoluxionescodechallenge.presentation.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jairbarzola.zoluxionescodechallenge.R
import com.jairbarzola.zoluxionescodechallenge.data.repository.MovieRepositoryImpl
import com.jairbarzola.zoluxionescodechallenge.data.service.MovieDataSourceImpl
import com.jairbarzola.zoluxionescodechallenge.domain.usecase.GetMoviesUseCase
import com.jairbarzola.zoluxionescodechallenge.presentation.home.DetailActivity.Companion.MOVIE_EXTRA
import com.jairbarzola.zoluxionescodechallenge.presentation.home.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by lazy {
        HomeViewModel(
            GetMoviesUseCase(
                MovieRepositoryImpl(
                    MovieDataSourceImpl()
                )
            )
        )
    }
    private lateinit var movieAdapter: MovieAdapter

    private var FIRST_PAGE = 1
    private var PAGES = FIRST_PAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupUI()
        setupObservers()
        getMovies()
    }

    private fun setupObservers() {
        viewModel.viewStateMutable.observe(this, Observer {
            when(it){
                is HomeResult.ShowLoading -> {
                    homeRefreshLayout.isRefreshing= true

                }
                is HomeResult.ShowError -> {
                    homeRefreshLayout.isRefreshing= false
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }

                is HomeResult.ShowMovies -> {
                    homeRefreshLayout?.isRefreshing= false
                    isLastPage = viewModel.isLastPage
                    movieAdapter.submitList(it.movies)
                    movieAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun setupUI() {
        movieAdapter = MovieAdapter()
        homeMoviesRecyclerView.run {
            adapter = movieAdapter
            addOnScrollListener(scrollListener)
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
        }
        movieAdapter.setOnItemClickListener {
            startActivity(Intent(this,DetailActivity::class.java).apply {
                putExtra(MOVIE_EXTRA,Gson().toJson(it))
            })
        }
        homeRefreshLayout.setOnRefreshListener {
            getMovies()
        }

    }

    private fun getMovies() {
        viewModel.movieResponse = null
        viewModel.getMovies(FIRST_PAGE)
    }

    var isLoading= false
    var isLastPage=false
    var isScrolling=false

    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNoLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition +visibleItemCount>= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition>=0
            val shouldPaginate = isNotLoadingAndNoLastPage && isAtLastItem
                    && isNotAtBeginning && isScrolling

            if(shouldPaginate){
                PAGES++
                viewModel.getMovies(PAGES)
                isScrolling=false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }
    }
}