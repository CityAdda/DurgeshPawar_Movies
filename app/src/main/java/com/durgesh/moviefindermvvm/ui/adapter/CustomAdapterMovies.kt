package com.durgesh.moviefindermvvm.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.durgesh.moviefindermvvm.R
import com.durgesh.moviefindermvvm.data.model.SearchResults

class CustomAdapterMovies :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var moviesList = ArrayList<SearchResults.SearchItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieViewHolder) {
            if (moviesList[position] != null) {
                holder.bindItems(moviesList[position]!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


    fun setData(newMoviesList: ArrayList<SearchResults.SearchItem?>?) {
        if (newMoviesList != null) {
            if (moviesList.isNotEmpty())
                moviesList.removeAt(moviesList.size - 1)
            moviesList.clear()
            moviesList.addAll(newMoviesList)
        } else {
            moviesList.add(newMoviesList)
        }
        notifyDataSetChanged()
    }

    fun getData() = moviesList

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imagePoster: ImageView = itemView.findViewById(R.id.image_poster)
        private val textTitle: TextView = itemView.findViewById(R.id.text_title)
        private val textYear: TextView = itemView.findViewById(R.id.text_year)

        @SuppressLint("SetTextI18n")
        fun bindItems(movie: SearchResults.SearchItem) {
            textTitle.text = movie.displayTitle
            textYear.text = movie.publicationDate
            Glide.with(imagePoster.context).load(movie.multimedia?.src)
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagePoster)
        }

    }


}