package com.durgesh.moviefindermvvm.ui.moviedetail


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.durgesh.moviefindermvvm.R
import com.durgesh.moviefindermvvm.databinding.ActivityMovieDetailScrollingBinding
import com.durgesh.moviefindermvvm.util.AppConstant
import kotlinx.android.synthetic.main.activity_movie_detail_scrolling.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MovieDetailScrollingActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private lateinit var dataBind: ActivityMovieDetailScrollingBinding
    private var movieTitle = ""
    private var moviePoster = ""
    private var movieYear = ""
    private var movieHeadline = ""
    private var movieSummery = ""
    private var movieRating = ""
    private var movieByLine = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail_scrolling)
        setSupportActionBar(toolbar)
        setupUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupUI() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra(AppConstant.INTENT_TITLE) && intent.getStringExtra(AppConstant.INTENT_TITLE) != null)
            movieTitle = intent.getStringExtra(AppConstant.INTENT_TITLE)!!
        if (intent.hasExtra(AppConstant.INTENT_POSTER) && intent.getStringExtra(AppConstant.INTENT_POSTER) != null)
            moviePoster = intent.getStringExtra(AppConstant.INTENT_POSTER)!!
        if (intent.hasExtra(AppConstant.INTENT_HEADLINE) && intent.getStringExtra(AppConstant.INTENT_HEADLINE) != null)
            movieHeadline = intent.getStringExtra(AppConstant.INTENT_HEADLINE)!!
        if (intent.hasExtra(AppConstant.INTENT_SUMMERY) && intent.getStringExtra(AppConstant.INTENT_SUMMERY) != null)
            movieSummery = intent.getStringExtra(AppConstant.INTENT_SUMMERY)!!


        dataBind.toolbar.title = movieTitle
        dataBind.textYear.text = "Year: $movieYear"
        dataBind.movieHeadline.text = "HeadLine: $movieHeadline"
        dataBind.movieSummery.text = "Summery: $movieSummery"


        Glide.with(this).load(moviePoster)
            .centerCrop()
            .thumbnail(0.5f)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(dataBind.imagePoster)

    }


}
