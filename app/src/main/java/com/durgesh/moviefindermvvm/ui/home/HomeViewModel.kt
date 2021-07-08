package com.durgesh.moviefindermvvm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.durgesh.moviefindermvvm.data.model.SearchResults
import com.durgesh.moviefindermvvm.data.repositories.HomeRepository
import com.durgesh.moviefindermvvm.util.ApiException
import com.durgesh.moviefindermvvm.util.AppConstant
import com.durgesh.moviefindermvvm.util.NoInternetException
import com.durgesh.moviefindermvvm.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {


    private var pageIndex = 0
    private var totalMovies = 0
    private var movieList = ArrayList<SearchResults.SearchItem?>()

    private val _moviesLiveData = MutableLiveData<State<ArrayList<SearchResults.SearchItem?>>>()
    val moviesLiveData: LiveData<State<ArrayList<SearchResults.SearchItem?>>>
        get() = _moviesLiveData

    private val _movieNameLiveData = MutableLiveData<String>()
    val movieNameLiveData: LiveData<String>
        get() = _movieNameLiveData

    private val _loadMoreListLiveData = MutableLiveData<Boolean>()
    val loadMoreListLiveData: LiveData<Boolean>
        get() = _loadMoreListLiveData

    private lateinit var movieResponse: SearchResults

    init {
        _loadMoreListLiveData.value = false
        _movieNameLiveData.value = ""
    }

    fun getMovies() {
        if (pageIndex == 1) {
            movieList.clear()
            _moviesLiveData.postValue(State.loading())
        } else {
            if (movieList.isNotEmpty() && movieList.last() == null)
                movieList.removeAt(movieList.size - 1)
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (_movieNameLiveData.value != null && _movieNameLiveData.value!!.isNotEmpty()) {
                try {
                    movieResponse = repository.getMovies(
                        _movieNameLiveData.value!!,
                        AppConstant.API_KEY
                    )
                    withContext(Dispatchers.Main) {
                        if (movieResponse.status == AppConstant.RESPONSE_SUCCESS) {
                            movieList.addAll(movieResponse.results)
                            totalMovies = movieResponse.numResults.toInt()
                            _moviesLiveData.postValue(State.success(movieList))
                            _loadMoreListLiveData.value = false
                        } else
                            _moviesLiveData.postValue(State.error(movieResponse.status))
                    }
                } catch (e: ApiException) {
                    withContext(Dispatchers.Main) {
                        _moviesLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                } catch (e: NoInternetException) {
                    withContext(Dispatchers.Main) {
                        _moviesLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                }
            }

        }
    }


    fun getAllMovies() {

        viewModelScope.launch(Dispatchers.IO) {
            if (_movieNameLiveData.value != null && _movieNameLiveData.value!!.isNotEmpty()) {
                try {
                    movieResponse = repository.getAllMovies(
                        AppConstant.API_KEY
                    )
                    withContext(Dispatchers.Main) {
                        if (movieResponse.status == AppConstant.RESPONSE_SUCCESS) {
                            movieList.addAll(movieResponse.results)
                            totalMovies = movieResponse.numResults.toInt()
                            _moviesLiveData.postValue(State.success(movieList))
                            _loadMoreListLiveData.value = false
                        } else
                            _moviesLiveData.postValue(State.error(movieResponse.status))
                    }
                } catch (e: ApiException) {
                    withContext(Dispatchers.Main) {
                        _moviesLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                } catch (e: NoInternetException) {
                    withContext(Dispatchers.Main) {
                        _moviesLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                }
            }

        }
    }

    fun searchMovie(query: String) {
        _movieNameLiveData.value = query
        getMovies()
    }

    fun checkForLoadMoreItems(
        visibleItemCount: Int,
        totalItemCount: Int,
        firstVisibleItemPosition: Int
    ) {
        if (!_loadMoreListLiveData.value!! && (totalItemCount < totalMovies)) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                _loadMoreListLiveData.value = true
            }
        }


    }


}